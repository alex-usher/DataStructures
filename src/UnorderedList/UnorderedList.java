package UnorderedList;

public class UnorderedList<T> implements UnorderedListStructure<T> {
  private final Node<T> start;
  private final Node<T> end;
  private int size;

  public UnorderedList() {
    this.start = new UnorderedListNode<T>(null, null);
    this.end = new UnorderedListNode<T>(null, null);
    start.setNext(end);
  }

  private Point<T> find(T item) {
    Node<T> pred = start;
    Node<T> curr = pred.next();

    while (curr != end) {
      if (curr.item().equals(item)) {
        break;
      }

      pred = curr;
      curr = curr.next();
    }

    return new Point<T>(pred, curr);
  }

  @Override
  public boolean add(T item) {
    Node<T> curr = start;

    while (curr.next() != end) {
      curr = curr.next();
    }

    // curr is the node before end so we can add the new node in between the two
    Node<T> newNode = new UnorderedListNode<T>(item, end);
    curr.setNext(newNode);

    size++;
    return true;
  }

  @Override
  public boolean remove(T item) {
    if(isEmpty()) {
      return false;
    } else {
      Point<T> point = find(item);
      Node<T> pred = point.pred;
      Node<T> curr = point.curr;

      if(curr.item().equals(item)) {
        pred.setNext(curr.next());
        size--;
        return true;
      } else {
        return false;
      }
    }
  }

  @Override
  public boolean contains(T item) {
    return find(item).curr != end;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public String toString() {
    Node<T> curr = start.next();
    StringBuilder sb = new StringBuilder();

    while(curr != end) {
      sb.append(String.format("%s ", curr.item()));
      curr = curr.next();
    }

    return sb.toString();
  }

  private static class Point<T> {
    private final Node<T> pred;
    private final Node<T> curr;

    public Point(Node<T> pred, Node<T> curr) {
      this.pred = pred;
      this.curr = curr;
    }
  }
}
