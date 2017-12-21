package pl.cendrzak.szymon.SubaruSsm;


public class SubaruParameter {
    private ParameterQuery query;
    private ValueConverter valueConverter;

    public SubaruParameter setQuery(ParameterQuery query) {
        this.query = query;
        return this;
    }

    public SubaruParameter setValueConverter(ValueConverter valueConverter) {
        this.valueConverter = valueConverter;
        return this;
    }

    public ParameterQuery getQuery() {
        return query;
    }

    public ValueConverter getValueConverter() {
        return valueConverter;
    }
}
