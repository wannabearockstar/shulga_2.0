package mapper.serializer;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface Serializer<T> {

	/**
	 * Serialize source object to string
	 *
	 * @param src source object
	 * @return serialized output
	 */
	String serialize(T src) throws IOException;

	/**
	 * Serialize source object to string and save to file system
	 *
	 * @param src source object
	 * @param to  path in file system
	 */
	void serialize(T src, String to) throws IOException;

	/**
	 * Serialize source object to string and save to stream
	 *
	 * @param src    source object
	 * @param writer abstract stream writer
	 */
	void serialize(T src, Writer writer) throws IOException;

	/**
	 * Deserialize input data into object
	 *
	 * @param data input data
	 * @return deserialized object
	 */
	T deserialize(String data) throws IOException;

	/**
	 * Deserialize input stream into object
	 *
	 * @param reader abstract stream reader
	 * @return deserialized object
	 */
	T deserialize(Reader reader) throws IOException;
}
