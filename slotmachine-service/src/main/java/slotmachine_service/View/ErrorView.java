package slotmachine_service.View;

import java.time.Instant;

public record ErrorView (
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path
) implements IErrorView {


        @Override
        public Instant getTimestamp() {return timestamp;}

        @Override
        public int getStatus() {return status;}

        @Override
        public String getError() {return error; }

        @Override
        public String getMessage() {return message;} 

        @Override
        public String getPath() {return path;}
}
