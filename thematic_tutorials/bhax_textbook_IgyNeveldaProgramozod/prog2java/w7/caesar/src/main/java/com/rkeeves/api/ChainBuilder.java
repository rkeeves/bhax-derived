package com.rkeeves.api;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ChainBuilder  {

    public static GeneratorStep builder(){
        return new ChainBuilderImpl();
    }

    private static class ChainBuilderImpl implements GeneratorStep, ChainMessageHandlersStep, FinishStep{

        private MessageGenerator messageGenerator;

        private final List<MessageHandler> handlers = new ArrayList<>();

        private Chain chain;

        @Override
        public ChainMessageHandlersStep messageGenerator(MessageGenerator messageGenerator) {
            this.messageGenerator = messageGenerator;
            return this;
        }

        @Override
        public ChainMessageHandlersStep chain(MessageHandler handler) {
            handlers.add(handler);
            return this;
        }

        @Override
        public FinishStep build() {
            this.chain = new Chain(messageGenerator,handlers);
            return this;
        }

        @Override
        public Chain get() {
            return chain;
        }
    }

    public interface GeneratorStep {
        ChainMessageHandlersStep messageGenerator(MessageGenerator messageGenerator);
    }

    public interface ChainMessageHandlersStep {

        ChainMessageHandlersStep chain(MessageHandler handler);

        FinishStep build();
    }

    public interface FinishStep {

        Chain get();
    }
}
