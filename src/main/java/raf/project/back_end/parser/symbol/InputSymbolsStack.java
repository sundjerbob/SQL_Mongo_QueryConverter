package raf.project.back_end.parser.symbol;

import java.util.List;
/**
* Basic forward linked list with elements of class Symbol,
 * it keeps track of the first in the list "head" or in stack analogy stacks bottom and of the last element in the list
 * witch is in stack analogy top of the stack.
* */
public class InputSymbolsStack {

    /**
     *
     */
    private Symbol head;
    private Symbol tail;




    public void push(Symbol symbol) {

        if (head == null) {
            head = symbol;
            tail = head;
        }
        tail.next = symbol;// current tail next is pointing to new symbol that should be pushed to the top/end
        tail = symbol;// updating current tail to the so the new tail is most recently pushed symbol
        tail.next = null;// to be sure setting the newly updated tails next to null so its for sure the top/end.

    }

    /**
     * This method moves the top of the stack to point to the symbol which has its next pointing to the current top,
     * so basically same as <code>stack.pop()</code> function new top becomes one below the current top.
     * @return Symbol instance currently on the top of the stack or th.
     */
    public Symbol swallow(){


        // || head.next == null
        if(head == tail) {
            Symbol swallowed = head; //this buffer for the return, because we will lose a reference
            head = null;// pop the head
            tail = null;// pop the tail
            return swallowed;// return what is to be swallowed by caller
        }

        //if list has more than one element it is iterated from the start and stopping at the one before the current top/tail
        Symbol newTail = head;//starting from head
        while(newTail.next != tail) {// if we are one before the last we stop there
            newTail = newTail.next;// go next we need to find the new tail
        }

        Symbol swallowed = tail;//store the to be swallowed symbol in the buffer, to save reference
        tail = newTail;// update the tail/top to point to one beneath itself
        tail.next = null;// setting its next to be null, so it's undeniably on the top/end;
        return swallowed;// return what we have swallowed


    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        Symbol curr = head;
        while (curr != null)
        {
            str.append("Symbol { type: ").append(curr.getTokenType()).append(", value: ").append(curr.getValue()).append(" }\n");
            curr = curr.next;
        }
        return str.toString();
    }

    public String popAllToStr(){
        StringBuilder str = new StringBuilder();
        Symbol curr = swallow();
        while (curr != null){
            str.append("Symbol { type: ").append(curr.getTokenType()).append(", value: ").append(curr.getValue()).append(" }\n");
            curr = swallow();
        }
        return str.toString();
    }
}

