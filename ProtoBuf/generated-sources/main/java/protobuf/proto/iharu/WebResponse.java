// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: WebResponseWrapper.proto

package protobuf.proto.iharu;

public final class WebResponse {
  private WebResponse() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_iharu_protobuf_WebResponseProto_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_iharu_protobuf_WebResponseProto_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_iharu_protobuf_HandshakeProto_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_iharu_protobuf_HandshakeProto_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\030WebResponseWrapper.proto\022\022org.iharu.pr" +
      "otobuf\"\312\001\n\020WebResponseProto\022;\n\006status\030\001 " +
      "\001(\0162+.org.iharu.protobuf.WebResponseProt" +
      "o.Status\022\013\n\003msg\030\002 \001(\t\022\017\n\007payload\030\003 \001(\014\022\014" +
      "\n\004sign\030\004 \001(\t\022\021\n\ttimestamp\030\005 \001(\003\":\n\006Statu" +
      "s\022\013\n\007FAILURE\020\000\022\013\n\007SUCCESS\020\001\022\t\n\005ERROR\020\002\022\013" +
      "\n\007UNKNOWN\020\003\"\\\n\016HandshakeProto\022\n\n\002pk\030\001 \001(" +
      "\014\022\r\n\005token\030\002 \001(\014\022\016\n\006config\030\003 \001(\014\022\014\n\004sign" +
      "\030\004 \001(\t\022\021\n\ttimestamp\030\005 \001(\003B%\n\024protobuf.pr" +
      "oto.iharuB\013WebResponseP\001b\006proto3"
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
    internal_static_org_iharu_protobuf_WebResponseProto_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_org_iharu_protobuf_WebResponseProto_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_iharu_protobuf_WebResponseProto_descriptor,
        new java.lang.String[] { "Status", "Msg", "Payload", "Sign", "Timestamp", });
    internal_static_org_iharu_protobuf_HandshakeProto_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_org_iharu_protobuf_HandshakeProto_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_iharu_protobuf_HandshakeProto_descriptor,
        new java.lang.String[] { "Pk", "Token", "Config", "Sign", "Timestamp", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
