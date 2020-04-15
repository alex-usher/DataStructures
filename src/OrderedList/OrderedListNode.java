package OrderedList;

public class OrderedListNode<T> implements Node<T> {
  private Node<T> next;
  private int key;
  private T item;

  public OrderedListNode(T item, int key, Node<T> next) {
    this.item = item;
    this.key = key;
    this.next = next;
  }

  public OrderedListNode(T item, int key) {
    this(item, key, null);
  }

  public OrderedListNode(T item) {
    this(item, item.hashCode(), null);
  }

  @Override
  public T item() {
    return item;
  }

  @Override
  public int key() {
    return key;
  }

  @Override
  public Node<T> next() {
    return next;
  }

  @Override
  public void setNext(Node<T> next) {
    this.next = next;
  }

  @Override
  public void setItem(T item) {
    this.item = item;
  }

  @Override
  public void key(int key) {
    this.key = key;
  }
}
