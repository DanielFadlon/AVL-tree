package coronatree;

public class AVLTree {

    private static int index = 0;    // global index for inorder function

    AVLNode root;    // The tree root.
    int size;        // The size of the tree.

    /**
     * Construct an empty tree.
     */
    public AVLTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Returns the size of the tree.
     *
     * @return the size of the tree.
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns the height of the tree.
     * Returns -1 if the tree is empty.
     *
     * @return the height of the tree.
     */
    public int height() {
        if (this.size == 0) {
            return -1;
        }
        return this.root.height;
    }

    /**
     * resets tree.
     */
    public void reset() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Inserts into the tree; You may assume that every person has a unique ID number.
     * That is, no person will appear twice.
     *
     * @param p - the person to insert.
     */
    public void insert(Person p) {
        AVLNode newNode = new AVLNode(p);
        AVLNode prev = null;
        AVLNode current = this.root;

        while (current != null) {
            prev = current;
            if (p.compareTo(current.data) < 0) {
                current = current.left;
            } else if (p.compareTo(current.data) > 0) {
                current = current.right;
            } else if (p.compareTo(current.data) == 0) {
                return;
            }
        }
        newNode.parent = prev;
        if (prev == null) {
            this.root = newNode;
        } else {
            if (p.compareTo(prev.data) > 0) {
                prev.right = newNode;
            } else {
                prev.left = newNode;
            }
            updateTree(newNode);
        }
        size++;
    }

    /**
     * update bf, height fields of ancestors of the given node .
     * balance the tree if required.
     *
     * @param x - first node to update.
     */
    private void updateTree(AVLNode x) {
        AVLNode prev = x;
        AVLNode current = x.parent;

        while (current != null) {
            if (current.right != null && prev.data.compareTo(current.right.data) == 0 && current.bf == -1) {
                rebalance(current);
                return;
            } else if (current.left != null && prev.data.compareTo(current.left.data) == 0 && current.bf == 1) {
                rebalance(current);
                return;
            } else {
                current.updateNode();
                if (current.height == prev.height) {
                    break;
                }
                prev = current;
                current = current.parent;
            }
        }
    }

    /**
     * balance the tree by calling the right function.
     *
     * @param x - the first node that has wrong bf field.
     */
    private void rebalance(AVLNode x) {
        if (x.bf > 0) {
            if (x.left.bf > 0) {
                rightRotate(x);
            } else if (x.left.bf < 0) {
                leftRightRotate(x);
            }
        }
        if (x.bf < 0) {
            if (x.right.bf < 0) {
                leftRotate(x);
            } else if (x.right.bf > 0) {
                rightLeftRotate(x);
            }
        }
    }

    /**
     * right to left rotate.
     *
     * @param q - node with wrong bf.
     */
    public void leftRotate(AVLNode q) {
        AVLNode p = q.right;
        if (p.left != null) {
            q.right = p.left;
            p.left.parent = q;
        } else {
            q.right = null;
        }

        if (q.parent != null) {
            if (q.data.compareTo(q.parent.data) > 0) {
                q.parent.right = p;
            } else {
                q.parent.left = p;
            }
        } else {
            this.root = p;
        }

        p.left = q;
        System.out.println("p -  " + p.height);
        System.out.println("q - h " + q.height);
        System.out.println();
        q.parent = p;

        q.updateNode();
        p.updateNode();

        System.out.println("p " + p.height);
        System.out.println("q -h " + q.height);
    }

    /**
     * left to right rotate.
     *
     * @param q - node with wrong bf.
     */
    public void rightRotate(AVLNode q) {
        AVLNode p = q.left;
        if (p.right != null) {
            q.left = p.right;
            p.right.parent = q;
        } else {
            q.left = null;
        }

        if (q.parent != null) {
            if (q.data.compareTo(q.parent.data) > 0) {
                q.parent.right = p;
            } else {
                q.parent.left = p;
            }
        } else {
            this.root = p;
        }

        p.right = q;
        q.parent = p;

        q.updateNode();
        p.updateNode();
    }

    /**
     * right - left rotate.
     *
     * @param x - node with wrong bf.
     */
    public void rightLeftRotate(AVLNode x) {
        AVLNode z = x.right;
        AVLNode y = z.left;
        AVLNode t3 = y.right;
        z.left = t3;
        y.right = z;
        z.parent = y;

        AVLNode t2 = y.left;
        x.right = t2;
        if (t2 != null) {
            t2.parent = x;
        }
        y.left = x;
        y.parent = x.parent;
        x.parent = y;

        if (y.parent == null) {
            this.root = y;
        }
        z.updateNode();
        x.updateNode();
        y.updateNode();
    }

    /**
     *  left - right rotate.
     *
     * @param x - node with wrong bf.
     */
    public void leftRightRotate(AVLNode x) {
        AVLNode z = x.left;
        AVLNode y = z.right;
        AVLNode t3 = y.left;
        z.right = t3;
        y.left = z;
        z.parent = y;

        AVLNode t2 = y.right;
        x.left = t2;
        if (t2 != null) {
            t2.parent = x;
        }
        y.right = x;
        y.parent = x.parent;
        x.parent = y;

        if (y.parent == null) {
            this.root = y;
        }
        z.updateNode();
        x.updateNode();
        y.updateNode();
    }

    /**
     * Search for a person in the tree.
     *
     * @param p the person to search for.
     * @return true iff 'p' is an element in the tree.
     */
    public boolean search(Person p) {
        return search(root, p);
    }

    /**
     * recursive helper function to Search for a person in the tree.
     *
     * @param node - current node to compare .
     * @param p the person to search for.
     * @return true iff the node data equals 'p'.
     */
    public boolean search(AVLNode node, Person p) {
        if (node == null) return false;
        else if (node.data.id == p.id) return true;
        else if (node.data.id > p.id) return search(node.left, p);
        else return search(node.right, p);
    }


    /**
     * Traverse the contents of this tree in an 'inorder' manner and return and array
     * containing the traversal of the tree.
     *
     * @return a sorted array of the tree's content.
     */
    public Person[] inorder() {
        Person[] person = new Person[size];
        index = 0;
        inorderTreeWalk(this.root, person);
        return person;
    }

    /**
     * recursive helper function for  'inorder'
     * insert nodes to destination array
     *
     * @param x - node to insert
     * @param person - destination array
     */
    private static void inorderTreeWalk(AVLNode x, Person[] person) {
        if (x != null) {
            inorderTreeWalk(x.left, person);
            person[index++] = x.data;
            inorderTreeWalk(x.right, person);
        }
   }


}
