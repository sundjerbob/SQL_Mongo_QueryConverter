package raf.project.back_end.parser.symbol;

/**
 * Basic forward linked list with elements of class Symbol,
 * it keeps track of the first in the list "head" or in stack analogy stacks bottom and of the last element in the list
 * witch is in stack analogy top of the stack.
 *
 * @author Tadija
 */
public class SymbolStack {


    private volatile Symbol head;
    private volatile Symbol tail;

    public SymbolStack() {
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    /**
     * This algorithm ensures that each new symbol added is placed at the beginning of the stack,
     * connecting itself to the remaining symbols.
     * As a result, when you later read the stack from the end to the beginning,
     * you will retrieve the symbols in the order in which they were added.
     *
     * @param symbol the symbol you want to add at as most recently read token.
     * @author Tadija.
     **/
    public void pushToBottom(Symbol symbol) {

        if (head == null) {
            head = symbol;
            tail = head;
            return;
        }

        symbol.next = head;
        head = symbol;

    }


    /**
     * This method gives you a look at Symbol you would get by performing <code>public Symbol swallow()</code> method call,
     * except it doesn't change the top/tail
     *
     * @return copy of the current symbol on the top of the stack, if the stack is empty, the return is "empty symbol";
     * that enables checking the next symbols type, which is what is method used for.
     * @apiNote By calling this method, caller can preview current stack top symbol,
     * but it's a reference to a copy of actual symbol, therefore, it is ensured that
     * the stack structure won't be changed a form outside.
     * We didn't give direct reference to tail ;<code>return this.tail;</code>
     * because we would risk somebody setting it to null.
     */
    public Symbol nextUp() {
        if(tail == null)
            return  new Symbol(null,"null");
        return new Symbol(tail.getTokenType(), tail.getValue());
    }


    /**
     * This method moves the top of the stack to point to the symbol which has its next pointing to the current top,
     * so basically same as <code>stack.pop()</code> function new top becomes one below the current top.
     *
     * @return Symbol instance currently on the top of the stack or th.
     */
    public Symbol swallow() {


        // || head.next == null
        if (head == tail) {
            // System.out.println( "head==tail" + head);
            Symbol swallowed = head; //this buffer for the return, because we will lose a reference
            head = null;// pop the head
            tail = null;// pop the tail

            return swallowed;// return what is to be swallowed by caller
        }
        //System.out.println( "else: " + head.getValue());
        //if list has more than one,
        // element it is iterated from the start and stopping at the one before the current top/tail
        Symbol newTail = head;//starting from head
        while (newTail.next != tail) {// if we are one before the last, we stop there
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
        while (curr != null) {
            str.append("Symbol { type: ").append(curr.getTokenType()).append(", value: ").append(curr.getValue()).append(" }\n");
            curr = curr.next;
        }
        return str.toString();
    }

    public String popAllToStr() {
        StringBuilder str = new StringBuilder();
        Symbol curr = swallow();

        while (curr != null) {
            str.append("Symbol { type: ").append(curr.getTokenType()).append(", value: ").append(curr.getValue()).append(" }\n");
            curr = swallow();
        }
        return str.toString();
    }
}

