package OrderedSet;

public class OrderedSetNode<T> implements Node<T> {

  private T item;
  private int key;
  private Node<T> next;

  public OrderedSetNode(T item, int key, Node<T> next) {
    this.next = next;
    this.item = item;
    this.key = key;
  }

  public OrderedSetNode(T item, int key) {
    this(item, key, null);
  }

  public OrderedSetNode(T item) {
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
  public void setKey(int key) {
    this.key = key;
  }

  @Override
  public void setItem(T item) {
    this.item = item;
  }
}
