package org.mangosbot.service.api;

import org.mangosbot.service.api.dto.LiveData;

public interface LiveDataService {

    LiveData get(long guid);

}
