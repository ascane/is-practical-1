package search;

public interface Frontier {
  public void addNode(Node node);
  public void clear();
  public void isEmpty();
  public Node removeNode();
}
