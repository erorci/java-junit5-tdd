package examples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ArrayStackTest {

    ArrayStack<String> stack;

    @BeforeEach
    void setupBeforeEachTestCase() {
        stack = ArrayStack.create();
    }

    @Test
    void testStaticArrayCreationMethods() {
        stack = ArrayStack.withInitialSize(1);
        assertThat(stack.getStackArraySize()).isEqualTo(1);

        stack = ArrayStack.withInitialAndMaxSize(1, 2);
        assertThat(stack.getStackArraySize()).isEqualTo(1);
        assertThat(stack.getMaxSize()).isEqualTo(2);
    }

    @Test
    @DisplayName("Array initialized has Zero items")
    void testArraySizeIsZeroOnCreation() {
        assertThat(stack.size()).isZero();
        assertThat(stack.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Single Item Pushed and then Peeked from the Array")
    void testPushSingleItemOnTheStackAndPeek() {
        final String itemPushed = "a";
        stack.push(itemPushed);
        assertThat(stack.size()).as("Stack Size").isEqualTo(1);
        assertThat(stack.peek()).isEqualTo(itemPushed);
    }

    @Test
    @DisplayName("Single Item Pushed and then Popped from the Array")
    void testPushSingleItemOnTheStackAndPop() {
        final String itemPushed = "a";
        stack.push(itemPushed);
        assertThat(stack.size()).as("Stack size").isEqualTo(1);
        assertThat(stack.pop()).isEqualTo(itemPushed);
        assertThat(stack.size()).as("Stack size").isZero();
    }

    @Test
    @DisplayName("Peek an Empty stack and thrown StackIsEmptyException")
    void testPeekShouldThrowAnExceptionOnEmptyStack() throws Exception {
        assertThatExceptionOfType(StackIsEmptyException.class).isThrownBy(() -> stack.peek());
    }

    @Test
    @DisplayName("Pop an Empty stack and thrown StackIsEmptyException")
    void testPopShouldThrowAnExceptionOnEmptyStack() throws Exception {
        assertThatExceptionOfType(StackIsEmptyException.class).isThrownBy(() -> stack.pop());
    }

    @Test
    @DisplayName("Push more items  should make the array grow")
    void testPushMoreThanInitialSizeStackArrayShouldGrow() {
        stack = ArrayStack.withInitialSize(1);
        stack.push("a");
        assertThat(stack.getStackArraySize()).isEqualTo(1);
        stack.push("b");
        assertThat(stack.getStackArraySize()).isEqualTo(2);
    }

    @Test
    @DisplayName("Push more than the Max size of items an Exception is thrown")
    void testPushMoreThanMaxCapacityShouldThrownAnException() {
        stack = ArrayStack.withInitialAndMaxSize(1, 1);
        stack.push("a");
        assertThat(stack.getStackArraySize()).isEqualTo(1);
        assertThatExceptionOfType(StackFullException.class).isThrownBy(() -> stack.push("b"));
    }

    @Test
    @DisplayName("Push Items no make array size bigger than Max size")
    void testPushItemsShouldNotIncreaseTheArraySizeMoreThanMax() {
        stack = ArrayStack.withInitialAndMaxSize(1, 5);
        stack.push("a");
        assertSizeAndTopItemOfStackArray(1, "a");
        stack.push("b");
        assertSizeAndTopItemOfStackArray(2, "b");
        stack.push("c");
        assertSizeAndTopItemOfStackArray(4, "c");
        stack.push("d");
        assertSizeAndTopItemOfStackArray(4, "d");
        stack.push("e");
        assertSizeAndTopItemOfStackArray(5, "e");
    }

    @Test
    @DisplayName("Pop multiple items will shrink the array size")
    void testPopMultipleItemsShouldShrinkArraySize() {
        stack = ArrayStack.withInitialAndMaxSize(2, 5);
        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.push("d");
        assertSizeAndTopItemOfStackArray(4, "d");
        assertThat(stack.pop()).isEqualTo("d");
        assertThat(stack.size()).isEqualTo(3);
        assertThat(stack.getStackArraySize()).isEqualTo(4);
        assertThat(stack.pop()).isEqualTo("c");
        assertThat(stack.size()).isEqualTo(2);
        assertThat(stack.getStackArraySize()).isEqualTo(2);
        assertThat(stack.pop()).isEqualTo("b");
        assertThat(stack.size()).isEqualTo(1);
        assertThat(stack.getStackArraySize()).isEqualTo(2);
    }

    private void assertSizeAndTopItemOfStackArray(int expectedStackArraySize, String expectItem) {
        assertThat(stack.getStackArraySize()).isEqualTo(expectedStackArraySize);
        assertThat(stack.peek()).isEqualTo(expectItem);
    }
}
