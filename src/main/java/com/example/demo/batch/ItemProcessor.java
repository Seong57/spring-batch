package com.example.demo.batch;

public interface ItemProcessor<I, O> {

    O process(I item);
}
