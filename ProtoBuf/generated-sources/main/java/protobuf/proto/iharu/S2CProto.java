// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: S2CProto.proto

package protobuf.proto.iharu;

public final class S2CProto {
  private S2CProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_iharu_protobuf_S2C_LoginProto_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_iharu_protobuf_S2C_LoginProto_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_iharu_protobuf_S2C_LoginProto_Authorization_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_iharu_protobuf_S2C_LoginProto_Authorization_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_iharu_protobuf_S2C_SSOProto_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_iharu_protobuf_S2C_SSOProto_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016S2CProto.proto\022\022org.iharu.protobuf\"\367\001\n" +
      "\016S2C_LoginProto\022\020\n\010username\030\001 \001(\t\022\r\n\005tok" +
      "en\030\002 \001(\014\022>\n\004auth\030\003 \003(\01320.org.iharu.proto" +
      "buf.S2C_LoginProto.Authorization\022\016\n\006conf" +
      "ig\030\004 \001(\014\032t\n\rAuthorization\0224\n\tauth_type\030\001" +
      " \001(\0162!.org.iharu.protobuf.AuthorityType\022" +
      "\r\n\005count\030\002 \001(\005\022\016\n\006expiry\030\003 \001(\003\022\016\n\006active" +
      "\030\004 \001(\003\"^\n\014S2C_SSOProto\022\017\n\007voucher\030\001 \001(\t\022" +
      "\014\n\004term\030\002 \001(\014\022\017\n\007payload\030\003 \001(\014\022\016\n\006expiry" +
      "\030\004 \001(\003\022\016\n\006config\030\005 \001(\014*\036\n\rAuthorityType\022" +
      "\r\n\tUNDEFINED\020\000B\"\n\024protobuf.proto.iharuB\010" +
      "S2CProtoP\001b\006proto3"
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
    internal_static_org_iharu_protobuf_S2C_LoginProto_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_org_iharu_protobuf_S2C_LoginProto_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_iharu_protobuf_S2C_LoginProto_descriptor,
        new java.lang.String[] { "Username", "Token", "Auth", "Config", });
    internal_static_org_iharu_protobuf_S2C_LoginProto_Authorization_descriptor =
      internal_static_org_iharu_protobuf_S2C_LoginProto_descriptor.getNestedTypes().get(0);
    internal_static_org_iharu_protobuf_S2C_LoginProto_Authorization_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_iharu_protobuf_S2C_LoginProto_Authorization_descriptor,
        new java.lang.String[] { "AuthType", "Count", "Expiry", "Active", });
    internal_static_org_iharu_protobuf_S2C_SSOProto_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_org_iharu_protobuf_S2C_SSOProto_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_iharu_protobuf_S2C_SSOProto_descriptor,
        new java.lang.String[] { "Voucher", "Term", "Payload", "Expiry", "Config", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
