package src.graphics;

/**
 * Linked list used for drawn sprites on screen to check for hit detection
 * @author Tyler Barton
 */
public class Node {
    public Node next, prev;

    /**
     * Unlinks the node from the list.
     * Used for deleted sprites.
     */
    public final void unlink() {
        // Check the node isn't the last in the list
        if (next != null) {
            prev.next = next;
            next.prev = prev;
            prev = null;
            next = null;
        }
    }
}
