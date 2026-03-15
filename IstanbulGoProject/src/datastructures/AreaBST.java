package datastructures;
import java.util.ArrayList;
import java.util.List;

public class AreaBST<T extends Comparable<T>> {
    private TreeNode<T> root;
    private static class TreeNode<T> {
        T data;
        TreeNode<T> left, right;
        TreeNode(T data) { this.data = data; }
    }
    public void insert(T data) { root = insertRec(root, data); }
    private TreeNode<T> insertRec(TreeNode<T> root, T data) {
        if (root == null) return new TreeNode<>(data);
        if (data.compareTo(root.data) < 0) root.left = insertRec(root.left, data);
        else if (data.compareTo(root.data) > 0) root.right = insertRec(root.right, data);
        return root;
    }
    public T search(T target) { return searchRec(root, target); }
    private T searchRec(TreeNode<T> root, T target) {
        if (root == null || target.compareTo(root.data) == 0) return (root != null) ? root.data : null;
        return (target.compareTo(root.data) < 0) ? searchRec(root.left, target) : searchRec(root.right, target);
    }
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        inOrder(root, list);
        return list;
    }
    private void inOrder(TreeNode<T> node, List<T> list) {
        if (node != null) { inOrder(node.left, list); list.add(node.data); inOrder(node.right, list); }
    }
}
