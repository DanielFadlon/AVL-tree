package coronatree;

public class AVLNode {
    Person data;		// The data in the node
    AVLNode parent;		// The parent
    AVLNode left;       // Left child
    AVLNode right;      // Right child
    int height;       	// Height
    int bf;             //balance factor

    /**
     * A standard constructor, sets all pointers to null.
     *
     * @param p - the data of the node.
     */
    AVLNode(Person p) {
        this.data = p;
        this.parent = null;
        this.left = null;
        this.right = null;
        this.height = 0;
        this.bf = 0;

    }

    /**
     * A standard constructor
     *
     * @param p - the data of the node.
     * @param lt - the left child.
     * @param rt - the right child.
     * @param parent - the parent.
     */
    AVLNode(Person p, AVLNode lt, AVLNode rt, AVLNode parent){
        this.data = p;
        this.parent = parent;
        this.left = lt;
        this.right = rt;
        updateNode();
    }

    /**
     * update Node bf , height fields
     *
     */
    public void updateNode(){
        if (left == null && right == null) {
            height = 0;
            bf = 0;
        } else if (left == null && right != null) {
            height = right.height + 1;
            bf = 0 - (right.height + 1);
        } else if (left != null && right == null) {
            height = left.height + 1;
            bf = left.height + 1;
        } else {
            height = Math.max(left.height, right.height) + 1;
            bf = left.height - right.height;
        }
    }

    public String toString(){

        return this.data.toString();
    }

}
