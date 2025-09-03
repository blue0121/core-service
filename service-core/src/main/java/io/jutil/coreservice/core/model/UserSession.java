package io.jutil.coreservice.core.model;

import io.jutil.coreservice.core.dict.Realm;
import io.jutil.springeasy.core.codec.CodecFactory;
import io.jutil.springeasy.core.codec.Decoder;
import io.jutil.springeasy.core.codec.Encoder;
import io.jutil.springeasy.core.codec.ExternalSerializable;
import io.jutil.springeasy.core.security.TokenException;
import io.jutil.springeasy.core.security.TokenUtil;
import io.jutil.springeasy.core.util.DateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;

/**
 * @author Jin Zheng
 * @since 2025-08-13
 */
@Getter
@Setter
@NoArgsConstructor
public class UserSession implements ExternalSerializable {
	private Realm realm;
	private long id;
	private LocalDateTime expireTime;
	private byte[] extension;


	public static UserSession createAndVerify(PublicKey key, String token) {
		var data = TokenUtil.verify(key, token);
		var session = new UserSession();
		CodecFactory.decode(session, data);
		session.checkExpireTime();
		return session;
	}

	private void checkExpireTime() {
		if (expireTime == null) {
			return;
		}
		var now = DateUtil.now();
		if (now.isAfter(expireTime)) {
			throw new TokenException("令牌无效");
		}
	}

	public String createToken(PrivateKey key) {
		var data = CodecFactory.encode(this);
		return TokenUtil.create(key, data);
	}

	@Override
	public void encode(Encoder encoder) {
		encoder.writeUInt(realm.getCode());
		encoder.writeLong(id);
		encoder.writeLocalDateTime(expireTime);
		if (extension == null || extension.length == 0) {
			encoder.writeInt(0);
		} else {
			encoder.writeInt(extension.length);
			encoder.writeBytes(extension);
		}
	}

	@Override
	public void decode(Decoder decoder) {
		this.realm = Realm.from(decoder.readUInt());
		this.id = decoder.readLong();
		this.expireTime = decoder.readLocalDateTime();
		int len = decoder.readInt();
		if (len > 0) {
			this.extension = new byte[len];
			decoder.readBytes(this.extension);
		}
	}
}
