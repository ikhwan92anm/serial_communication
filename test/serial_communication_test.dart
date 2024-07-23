import 'package:flutter_test/flutter_test.dart';
import 'package:serial_communication/serial_communication.dart';
import 'package:serial_communication/serial_communication_platform_interface.dart';
import 'package:serial_communication/serial_communication_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockSerialCommunicationPlatform with MockPlatformInterfaceMixin implements SerialCommunicationPlatform {
  @override
  Future<List<String>?> getAvailablePorts() {
    throw UnimplementedError();
  }

  @override
  Future<String?> clearLog() {
    throw UnimplementedError();
  }

  @override
  Future<String?> clearReadPort1() {
    throw UnimplementedError();
  }

  @override
  Future<String?> clearReadPort2() {
    throw UnimplementedError();
  }

  @override
  Future<String?> closePort1() {
    throw UnimplementedError();
  }

  @override
  Future<String?> closePort2() {
    throw UnimplementedError();
  }

  @override
  Future<String?> destroyResources() {
    throw UnimplementedError();
  }

  @override
  Future<String?> openPort1({required DataFormat dataFormat, required String serialPort, required int baudRate}) {
    throw UnimplementedError();
  }

  @override
  Future<String?> openPort2({required DataFormat dataFormat, required String serialPort, required int baudRate}) {
    throw UnimplementedError();
  }

  @override
  Future<String?> sendCommandPort1({required String message}) {
    throw UnimplementedError();
  }

  @override
  Future<String?> sendCommandPort2({required String message}) {
    throw UnimplementedError();
  }

  @override
  Stream<SerialResponse> startSerial() {
    throw UnimplementedError();
  }
}

void main() {
  final SerialCommunicationPlatform initialPlatform = SerialCommunicationPlatform.instance;

  test('$MethodChannelSerialCommunication is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelSerialCommunication>());
  });
}
