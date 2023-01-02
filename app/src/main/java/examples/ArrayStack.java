package examples;

import java.util.Arrays;

public class ArrayStack<T> {
    public static final int DEFAULT_INITIAL_SIZE = 16;
    private static final int DEFAULT_MAX_SIZE = Integer.MAX_VALUE;
    private final int maxSize;
    private final int initialSize;
    private T[] itemsArray;
    private int top = 0;

    private ArrayStack(int initialSize) {
        this(initialSize, DEFAULT_MAX_SIZE);
    }

    @SuppressWarnings("unchecked")
    private ArrayStack(int initialSize, int maxSize) {
        this.initialSize = initialSize;
        this.maxSize = maxSize;
        itemsArray = (T[]) new Object[initialSize];
    }

    public static <T> ArrayStack<T> create() {
        return new ArrayStack<>(DEFAULT_INITIAL_SIZE);
    }

    public static <T> ArrayStack<T> withInitialSize(int initialSize) {
        return new ArrayStack<>(initialSize);
    }

    public static <T> ArrayStack<T> withInitialAndMaxSize(int initialSize, int maxSize) {
        // TODO implement tests to validate input parameters else thrown exception.
        return new ArrayStack<>(initialSize, maxSize);
    }

    public void push(T item) {
        isReachedMaxSize();
        growStackArraySizeIfRequired();
        itemsArray[top] = item;
        top++;
    }

    private void isReachedMaxSize() {
        if (top == maxSize) {
            throw new StackFullException();
        }
    }

    private void growStackArraySizeIfRequired() {
        if (top == getStackArraySize()) {
            int newArrayLength = getStackArraySize() * 2;
            itemsArray = Arrays.copyOf(itemsArray, Math.min(newArrayLength, maxSize));
        }
    }

    public T pop() { //Always removed the top element
        T itemOnTop = peek();
        top--;
        itemsArray[top] = null; //GC will take care of dereference it.
        shrinkArraySizeAsRequired();

        return itemOnTop;
    }

    private void shrinkArraySizeAsRequired() {
        int newReducedArrayLength = getStackArraySize() / 2;
        if (top <= newReducedArrayLength && newReducedArrayLength >= initialSize) {
            itemsArray = Arrays.copyOf(itemsArray, newReducedArrayLength);
        }
    }

    public T peek() {
        if (isEmpty()) {
            throw new StackIsEmptyException();
        }
        return itemsArray[top - 1];
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return top;
    }

    int getStackArraySize() {
        return itemsArray.length;
    }

    int getMaxSize() {
        return maxSize;
    }
}
