// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: WebResponseWrapper.proto

package protobuf.proto.iharu;

public interface WebResponseProtoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:org.iharu.protobuf.WebResponseProto)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.org.iharu.protobuf.WebResponseProto.Status status = 1;</code>
   */
  int getStatusValue();
  /**
   * <code>.org.iharu.protobuf.WebResponseProto.Status status = 1;</code>
   */
  protobuf.proto.iharu.WebResponseProto.Status getStatus();

  /**
   * <code>string msg = 2;</code>
   */
  java.lang.String getMsg();
  /**
   * <code>string msg = 2;</code>
   */
  com.google.protobuf.ByteString
      getMsgBytes();

  /**
   * <code>bytes payload = 3;</code>
   */
  com.google.protobuf.ByteString getPayload();

  /**
   * <code>string sign = 4;</code>
   */
  java.lang.String getSign();
  /**
   * <code>string sign = 4;</code>
   */
  com.google.protobuf.ByteString
      getSignBytes();

  /**
   * <code>int64 timestamp = 5;</code>
   */
  long getTimestamp();
}
