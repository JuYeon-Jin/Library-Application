package com.group.libraryapp.project.domain.user;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

/**
 * This class provides conversion between UUID and String for use in JPA entity mapping.
 * 이 클래스는 JPA 엔터티 매핑에 사용되는 UUID와 String 간의 변환을 제공합니다.
 */
@Converter(autoApply = true)
public class UUIDConverter implements AttributeConverter<UUID, String> {

    /**
     * Converts a UUID to its string representation for database storage.
     * UUID를 데이터베이스에 저장하기 위해 문자열로 변환합니다.
     *
     * @param uuid The UUID to convert. (uuid 변환할 UUID)
     * @return The string representation of the UUID, or null if the input UUID is null. (UUID의 문자열 표현, 입력 UUID가 null인 경우 null을 반환합니다.)
     */
    @Override
    public String convertToDatabaseColumn(UUID uuid) {
        return (uuid == null) ? null : uuid.toString();
    }

    /**
     * Converts a string from the database to a UUID.
     * 데이터베이스에서 가져온 문자열을 UUID로 변환합니다.
     *
     * @param uuidString The string representation of the UUID. (uuidString UUID의 문자열 표현)
     * @return The UUID represented by the input string, or null if the input string is null. (입력 문자열이 null인 경우 null을 반환하고, 그렇지 않으면 문자열을 UUID로 변환하여 반환합니다.)
     */
    @Override
    public UUID convertToEntityAttribute(String uuidString) {
        return (uuidString == null) ? null : UUID.fromString(uuidString);
    }
}

