package pl.cendrzak.szymon.SubaruSsm;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Szymon on 2014-12-11.
 */
public class SubaruDataProcessor {

    private enum State
    {
        NOTHING_RECEIVED,
        MSB_RECEIVED,
        LSB_RECEIVED,
        DATA_RECEIVED,
    }

    //private SubaruParameter currentRequestedParameter;
    private SubaruQueryConstructor subaruQueryConstructor;
    private ArrayList<Byte> receivedData = new ArrayList<Byte>();
    private State currentState = State.NOTHING_RECEIVED;

    public SubaruDataProcessor(SubaruQueryConstructor subaruQueryConstructor)
    {
        this.subaruQueryConstructor = subaruQueryConstructor;
    }

    public int processDataForParameter(SubaruParameter parameter)
    {
        Iterator<Byte> iterator = receivedData.iterator();
        byte requestedMsb = subaruQueryConstructor.getMsbForParameter(parameter);
        byte requestedLsb = subaruQueryConstructor.getLsbForParameter(parameter);
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
                return -1;
            } else if (currentState == State.MSB_RECEIVED) {
                if(singleByte == requestedLsb) {
                    currentState = State.LSB_RECEIVED;
                } else {
                    currentState = State.NOTHING_RECEIVED;
                }
                iterator.remove();
                return -1;
            } else if (currentState == State.LSB_RECEIVED) {
                currentState = State.DATA_RECEIVED;
                int unsignedByteValue = (singleByte & 0xFF);
                int calculatedValue = convertReceivedData(unsignedByteValue, parameter);
                iterator.remove();
                return calculatedValue;
            }
        }
        return -1;
    }

    private int convertReceivedData(int data, SubaruParameter parameter)
    {
        switch(parameter) {
            case ENGINE_SPEED:
                return data * 25;
            case ENGINE_LOAD:
                return data;
            case ENGINE_TEMP:
                return data - 50;
            case END_CONNECTION:
                return -1;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void appendToReceivedData(byte[] newData) {
        for(byte singleByte : newData) {
            receivedData.add(singleByte);
        }
    }


}
