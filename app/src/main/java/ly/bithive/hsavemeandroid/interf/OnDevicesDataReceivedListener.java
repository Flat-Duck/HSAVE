package ly.bithive.hsavemeandroid.interf;

import java.util.List;

import ly.bithive.hsavemeandroid.model.Device;

public interface OnDevicesDataReceivedListener {
    void onDeviceDataReceived(List<Device> deviceList);
}
