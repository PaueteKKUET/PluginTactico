package com.fadedbytes.PluginTactico.Tags;

import java.util.Iterator;

public class TagIterator<T> implements Iterator<T> {

    private int index;
    private final TagTactica<T> tag;

    public TagIterator(TagTactica<T> tag) {
        index = 0;
        this.tag = tag;
    }

    @Override
    public boolean hasNext() {
        return index < tag.size();
    }

    @Override
    public T next() {
        this.index++;
        return tag.ITEMS.get(index - 1);
    }
}
