package pl.cendrzak.szymon.SubaruSsm;


public class ValueConverter {
    private Operator operation;
    private Double value;

    public Double convertValue(Integer valueToConvert)
    {
        switch(operation) {
            case ADD:
                return valueToConvert + value;
            case SUBTRACT:
                return valueToConvert - value;
            case MULTIPLY:
                return valueToConvert * value;
            case DIVIDE:
                return valueToConvert / value;
            default:
                throw new IllegalArgumentException();
        }
    }

    public ValueConverter setOperation(Operator operation) {
        this.operation = operation;
        return this;
    }

    public ValueConverter setValue(Double value) {
        this.value = value;
        return this;
    }
}
