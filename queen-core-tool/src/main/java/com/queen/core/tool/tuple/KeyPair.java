package com.queen.core.tool.tuple;

import com.queen.core.tool.utils.RsaUtil;
import lombok.RequiredArgsConstructor;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * rsa 的 key pair 封装
 *
 * @author jensen
 */
@RequiredArgsConstructor
public class KeyPair {
	private final java.security.KeyPair keyPair;

	public PublicKey getPublic() {
		return keyPair.getPublic();
	}

	public PrivateKey getPrivate() {
		return keyPair.getPrivate();
	}

	public byte[] getPublicBytes() {
		return this.getPublic().getEncoded();
	}

	public byte[] getPrivateBytes() {
		return this.getPrivate().getEncoded();
	}

	public String getPublicBase64() {
		return RsaUtil.getKeyString(this.getPublic());
	}

	public String getPrivateBase64() {
		return RsaUtil.getKeyString(this.getPrivate());
	}

	@Override
	public String toString() {
		return "PublicKey=" + this.getPublicBase64() + '\n' + "PrivateKey=" + this.getPrivateBase64();
	}
}
