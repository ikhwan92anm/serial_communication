import 'dart:convert';

import 'serial_communication_platform_interface.dart';

///DataFormat for TX and RX
enum DataFormat { ASCII, HEX_STRING }

/// SerialCommunication handles the serial communication.
class SerialCommunication {
  Stream<SerialResponse> startSerial() {
    return SerialCommunicationPlatform.instance.startSerial();
  }

  /// [openPort] method to call the open api with the specified [dataFormat],[serialPort],[baudRate].
  /// The [dataFormat] used will be [ASCII], unless otherwise
  /// specified.
  ///
  /// The [serialPort] and [baudRate] arguments cannot be null.
  Future<String?> openPort1({DataFormat? dataFormat, required String serialPort, required int baudRate}) {
    return SerialCommunicationPlatform.instance.openPort1(dataFormat: dataFormat ?? DataFormat.ASCII, serialPort: serialPort, baudRate: baudRate);
  }

  Future<String?> openPort2({DataFormat? dataFormat, required String serialPort, required int baudRate}) {
    return SerialCommunicationPlatform.instance.openPort2(dataFormat: dataFormat ?? DataFormat.ASCII, serialPort: serialPort, baudRate: baudRate);
  }

  /// [getAvailablePorts] : Listing the available serial ports on the device,
  /// including USB to serial adapters
  Future<List<String>?> getAvailablePorts() {
    return SerialCommunicationPlatform.instance.getAvailablePorts();
  }

  /// [closePort] : close the opened port
  /// if you opened any port and want other available serial port
  /// call this closePort api to close the previous port
  Future<String?> closePort1() {
    return SerialCommunicationPlatform.instance.closePort1();
  }

  Future<String?> closePort2() {
    return SerialCommunicationPlatform.instance.closePort2();
  }

  /// Creates a [sendCommand] with the specified [message].
  /// used as TX transmitter
  Future<String?> sendCommandPort1({required String message}) {
    return SerialCommunicationPlatform.instance.sendCommandPort1(message: message);
  }

  Future<String?> sendCommandPort2({required String message}) {
    return SerialCommunicationPlatform.instance.sendCommandPort2(message: message);
  }

  /// [clearLog] method will clear the Log channel
  Future<String?> clearLog() {
    return SerialCommunicationPlatform.instance.clearLog();
  }

  /// [clearRead] method will clear the Read channel
  Future<String?> clearReadPort1() {
    return SerialCommunicationPlatform.instance.clearReadPort1();
  }

  Future<String?> clearReadPort2() {
    return SerialCommunicationPlatform.instance.clearReadPort2();
  }

  /// [destroy] method will clear resources and destroy
  /// the background threads
  Future<String?> destroy() {
    return SerialCommunicationPlatform.instance.destroyResources();
  }

  ///Standard baud rates list
  ///it will return the integer list of standard baud rate
  final List<int> baudRateList = [50, 75, 110, 134, 150, 200, 300, 600, 1200, 1800, 2400, 4800, 9600, 14400, 19200, 38400, 57600, 115200, 128000, 230400, 460800, 500000, 576000, 921600, 1000000, 1152000, 1500000, 2000000, 2500000, 3000000, 3500000, 4000000];
}

class SerialResponse {
  final String? logChannelPort1;
  final String? readChannelPort1;
  final String? logChannelPort2;
  final String? readChannelPort2;
  SerialResponse({
    this.logChannelPort1,
    this.readChannelPort1,
    this.logChannelPort2,
    this.readChannelPort2,
  });

  factory SerialResponse.fromMap(Map<String, dynamic> map) {
    return SerialResponse(
      logChannelPort1: map['logChannelPort1'],
      readChannelPort1: map['readChannelPort1'],
      logChannelPort2: map['logChannelPort2'],
      readChannelPort2: map['readChannelPort2'],
    );
  }

  factory SerialResponse.fromJson(String source) => SerialResponse.fromMap(json.decode(source));
}
