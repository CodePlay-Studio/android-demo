/*
 * Copyright 2017 (C) CodePlay Studio. All rights reserved.
 *
 * All source code within this app is licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package my.com.codeplay.android_demo.objects;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class Item {
    private int thumbnailId;
    private int titleId;
    private int shortDescId;

    public Item(@DrawableRes int thumbnailId, @StringRes int titleId, @StringRes int shortDescId) {
        this.thumbnailId = thumbnailId;
        this.titleId = titleId;
        this.shortDescId = shortDescId;
    }

    public int getThumbnailId() {
        return thumbnailId;
    }

    public void setThumbnailId(int thumbnailId) {
        this.thumbnailId = thumbnailId;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public int getShortDescId() {
        return shortDescId;
    }

    public void setShortDescId(int shortDescId) {
        this.shortDescId = shortDescId;
    }
}
