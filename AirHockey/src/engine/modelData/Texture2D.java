package engine.modelData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.joml.Vector3i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;

public class Texture2D {
	private int id;
	private int slot = 0;
	
	public Texture2D(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public void setSlot(int slot) {
		this.slot = slot;
	}
	
	public void bind() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + slot);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	public void unbind() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + slot);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public void release() {
		GL11.glDeleteTextures(id);
	}

	public static Texture2D loadFromFile(String filename) {
		PNGDecoder decoder;
		try {
			decoder = new PNGDecoder(new FileInputStream(new File(filename)));
			ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());

			decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
			buffer.flip();

			int id = GL11.glGenTextures();

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL13.GL_TEXTURE_MIN_FILTER, GL13.GL_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL13.GL_TEXTURE_MAG_FILTER, GL13.GL_LINEAR);

			GL11.glTexImage2D(GL13.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0,
					GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			return new Texture2D(id);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new Texture2D(0);
	}
	
	public static Texture2D createFromColor(Vector3i color) {
		return createFromColor(color.x, color.y, color.z);
	}
	
	public static Texture2D createFromColor(int r, int g, int b) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(4 * 1 * 1);
		buffer.put((byte)r);
		buffer.put((byte)g);
		buffer.put((byte)b);
		buffer.put((byte)255);
		buffer.flip();
		
		int id = GL11.glGenTextures();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL13.GL_TEXTURE_MIN_FILTER, GL13.GL_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL13.GL_TEXTURE_MAG_FILTER, GL13.GL_LINEAR);

		GL11.glTexImage2D(GL13.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 1, 1, 0,
				GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return new Texture2D(id);
	}
}
