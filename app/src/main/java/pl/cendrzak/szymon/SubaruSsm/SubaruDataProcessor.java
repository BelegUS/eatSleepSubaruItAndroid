package pl.cendrzak.szymon.SubaruSsm;

import java.util.ArrayList;
import java.util.Iterator;

public class SubaruDataProcessor {

    private enum State
    {
        NOTHING_RECEIVED,
        MSB_RECEIVED,
        LSB_RECEIVED,
        DATA_RECEIVED,
    }

    private SubaruParameter requestedParameter;
    private ArrayList<Byte> receivedData = new ArrayList<Byte>();
    private State currentState = State.NOTHING_RECEIVED;

    public Double processData()
    {
        Iterator<Byte> iterator = receivedData.iterator();
        byte requestedMsb = requestedParameter.getQuery().getMsb();
        byte requestedLsb = requestedParameter.getQuery().getLsb();
        while(iterator.hasNext()) {
            byte singleByte = iterator.next();
            //First byte ever or new set of bytes incoming - check if received is MSB
            if(currentState == State.DATA_RECEIVED || currentState == State.NOTHING_RECEIVED) {
                if(singleByte == requestedMsb) {
                    currentState = State.MSB_RECEIVED;
                } else {
                    currentState = State.NOTHING_RECEIVED;
                }
                iterator.remove();
                return (double)-1;
            } else if (currentState == State.MSB_RECEIVED) {
                if(singleByte == requestedLsb) {
                    currentState = State.LSB_RECEIVED;
                } else {
                    currentState = State.NOTHING_RECEIVED;
                }
                iterator.remove();
                return (double)-1;
            } else if (currentState == State.LSB_RECEIVED) {
                currentState = State.DATA_RECEIVED;
                int unsignedByteValue = (singleByte & 0xFF);
                Double calculatedValue = requestedParameter.getValueConverter().convertValue(unsignedByteValue);
                iterator.remove();
                return calculatedValue;
            }
        }
        return (double)-1;
    }

    public void appendToReceivedData(byte[] newData) {
        for(byte singleByte : newData) {
            receivedData.add(singleByte);
        }
    }

    public void setRequestedParameter(SubaruParameter requestedParameter)
    {
        this.requestedParameter = requestedParameter;
    }

    public SubaruParameter getRequestedParameter() {
        return requestedParameter;
    }
}
