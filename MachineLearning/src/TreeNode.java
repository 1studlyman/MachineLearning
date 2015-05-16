/*
 * 
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * Copyright (C) 2003-2004 TONBELLER AG.
 * All Rights Reserved.
 * You must accept the terms of that agreement to use this software.
 * 
 *
 * 
 */


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import weka.core.Instances;

/**
 * Tree Node for the for a general tree of Objects
 */
public class TreeNode {

  protected TreeNode parent = null;
  protected List<TreeNode> children = null;
  protected Object reference;
  
  
  //Below is the stuff I added
	Instances instances;
	int attributeIndexUsed = -1;
	double  entropy = 1;

	public TreeNode(Object obj, Instances pInstances) {
		super();
		this.instances = pInstances;
		
	    this.parent = null;
	    this.reference = obj;
	    this.children = new ArrayList<TreeNode>();    
	}
  
	public double calculateEntropy(Instances instances)
	{
		if (instances.numClasses() <= 1)
			return 0;
		else
		{
			int numInstances = instances.numInstances();
			int numClasses   = instances.numClasses();
			
			//Count how many in each class
			int[] classCounts = new int[numClasses];
			for (int i = 0; i < numInstances; i++)
			{
				classCounts[(int) instances.instance(i).classValue()]++;
			}
			
			//Calculate the entropy
			double entropy = 0;
			double quotient;
			for (int i = 0; i < numClasses; i++)
			{
				double result;
				if (classCounts[i] == 0)
				{
					result = 0;
				}
				else
				{
					quotient = (double)classCounts[i] / (double)numInstances;
					result = (quotient * Math.log(quotient) / Math.log(numClasses));
					assert (Double.isNaN(result) && result <= 1);
				}
				
				entropy = entropy - result;
			}
			return entropy;
		}
	}
	
	//Through to here
  
  /**
   * cTtor
   * @param obj referenced object
   */
  public TreeNode(Object obj) {
    this.parent = null;
    this.reference = obj;
    this.children = new ArrayList<TreeNode>();
  }

  /**
   * remove node from tree
   */
  public void remove() {
    if (parent != null) {
      parent.removeChild(this);
    }
  }

  /**
   * remove child node
   * @param child
   */
  private void removeChild(TreeNode child) {
    if (children.contains(child))
      children.remove(child);

  }

  /**
   * add child node
   * @param child node to be added
   */
  public void addChildNode(TreeNode child) {
    child.parent = this;
    if (!children.contains(child))
      children.add(child);
  }

  /**
   * deep copy (clone)
   * @return copy of TreeNode
   */
  public TreeNode deepCopy() {
    TreeNode newNode = new TreeNode(reference);
    for (Iterator<TreeNode> iter = children.iterator(); iter.hasNext();) {
      TreeNode child = (TreeNode) iter.next();
      newNode.addChildNode(child.deepCopy());
    }
    return newNode;
  }

  /**
   * deep copy (clone) and prune 
   * @param depth - number of child levels to be copied
   * @return copy of TreeNode
   */
  public TreeNode deepCopyPrune(int depth) {
    if (depth < 0)
      throw new IllegalArgumentException("Depth is negative");
    TreeNode newNode = new TreeNode(reference);
    if (depth == 0)
      return newNode;
    for (Iterator<TreeNode> iter = children.iterator(); iter.hasNext();) {
      TreeNode child = (TreeNode) iter.next();
      newNode.addChildNode(child.deepCopyPrune(depth - 1));
    }
    return newNode;
  }

  /**
   * @return level = distance from root
   */
  public int getLevel() {
    int level = 0;
    TreeNode p = parent;
    while (p != null) {
      ++level;
      p = p.parent;
    }
    return level;
  }

  /**
   * walk through subtree of this node
   * @param callbackHandler function called on iteration 
   */
  public int walkTree(TreeNodeCallback callbackHandler) {
    int code = 0;
    code = callbackHandler.handleTreeNode(this);
    if (code != TreeNodeCallback.CONTINUE)
      return code;
    ChildLoop: for (Iterator<TreeNode> iter = children.iterator(); iter.hasNext();) {
      TreeNode child = (TreeNode) iter.next();
      code = child.walkTree(callbackHandler);
      if (code >= TreeNodeCallback.CONTINUE_PARENT)
        return code;
    }
    return code;
  }

  /**
   * walk through children subtrees of this node
   * @param callbackHandler function called on iteration 
   */
  public int walkChildren(TreeNodeCallback callbackHandler) {
    int code = 0;
    ChildLoop: for (Iterator<TreeNode> iter = children.iterator(); iter.hasNext();) {
      TreeNode child = (TreeNode) iter.next();
      code = callbackHandler.handleTreeNode(child);
      if (code >= TreeNodeCallback.CONTINUE_PARENT)
        return code;
      if (code == TreeNodeCallback.CONTINUE) {
        code = child.walkChildren(callbackHandler);
        if (code > TreeNodeCallback.CONTINUE_PARENT)
          return code;
      }
    }
    return code;
  }

  /**
   * @return List of children
   */
  public List<TreeNode> getChildren() {
    return children;
  }

  /**
   * @return parent node
   */
  public TreeNode getParent() {
    return parent;
  }

  /**
   * @return reference object
   */
  public Object getReference() {
    return reference;
  }

  /**
   * set reference object
   * @param object reference
   */
  public void setReference(Object object) {
    reference = object;
  }

} // TreeNode

/*
 * 
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * Copyright (C) 2003-2004 TONBELLER AG.
 * All Rights Reserved.
 * You must accept the terms of that agreement to use this software.
 * 
 *
 * 
 */

/**
 * handle call back for position tree
 */
interface TreeNodeCallback {

  public static final int CONTINUE = 0;
  public static final int CONTINUE_SIBLING = 1;
  public static final int CONTINUE_PARENT = 2;
  public static final int BREAK = 3;

  /**
   * @param node the current node to handle
   * @return 0 continue tree walk
   *         1 break this node (continue sibling)
   *         2 break this level (continue parent level)
   *         3 break tree walk 
   */
  int handleTreeNode(TreeNode node);
} // TreeNodeCallback
