package pl.cendrzak.szymon.SubaruSsm;


public class SubaruParameter {
    private SubaruQuery query;
    private ValueConverter valueConverter;

    public SubaruParameter setQuery(SubaruQuery query) {
        this.query = query;
        return this;
    }

    public SubaruParameter setValueConverter(ValueConverter valueConverter) {
        this.valueConverter = valueConverter;
        return this;
    }

    public SubaruQuery getQuery() {
        return query;
    }

    public ValueConverter getValueConverter() {
        return valueConverter;
    }
}
