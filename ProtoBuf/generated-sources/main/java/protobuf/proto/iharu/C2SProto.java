// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: C2SProto.proto

package protobuf.proto.iharu;

public final class C2SProto {
  private C2SProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_iharu_protobuf_C2S_LoginProto_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_iharu_protobuf_C2S_LoginProto_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_iharu_protobuf_C2S_SSOProto_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_iharu_protobuf_C2S_SSOProto_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016C2SProto.proto\022\022org.iharu.protobuf\"v\n\016" +
      "C2S_LoginProto\022\020\n\010username\030\001 \001(\t\022\020\n\010pass" +
      "word\030\002 \001(\t\022\021\n\ttimestamp\030\003 \001(\003\022\014\n\004inet\030\004 " +
      "\001(\t\022\017\n\007macaddr\030\005 \001(\t\022\016\n\006config\030\006 \001(\014\"\217\001\n" +
      "\014C2S_SSOProto\022\r\n\005token\030\001 \001(\t\022\017\n\007voucher\030" +
      "\002 \001(\t\022\014\n\004term\030\003 \001(\014\022\017\n\007payload\030\004 \001(\014\022\021\n\t" +
      "timestamp\030\005 \001(\003\022\014\n\004inet\030\006 \001(\t\022\017\n\007macaddr" +
      "\030\007 \001(\t\022\016\n\006config\030\010 \001(\014B\"\n\024protobuf.proto" +
      ".iharuB\010C2SProtoP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_org_iharu_protobuf_C2S_LoginProto_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_org_iharu_protobuf_C2S_LoginProto_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_iharu_protobuf_C2S_LoginProto_descriptor,
        new java.lang.String[] { "Username", "Password", "Timestamp", "Inet", "Macaddr", "Config", });
    internal_static_org_iharu_protobuf_C2S_SSOProto_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_org_iharu_protobuf_C2S_SSOProto_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_iharu_protobuf_C2S_SSOProto_descriptor,
        new java.lang.String[] { "Token", "Voucher", "Term", "Payload", "Timestamp", "Inet", "Macaddr", "Config", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
