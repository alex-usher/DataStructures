package UnorderedList;

public class UnorderedListNode<T> implements Node<T> {
  private T item;
  private Node<T> next;

  public UnorderedListNode(T item, Node<T> next) {
    this.next = next;
    this.item = item;
  }

  public UnorderedListNode(T item) {
    this(item, null);
  }

  @Override
  public T item() {
    return item;
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
}
