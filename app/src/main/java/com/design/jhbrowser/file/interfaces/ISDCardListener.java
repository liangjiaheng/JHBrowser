/**
 * Copyright (C) 2012 yanghui <yanghui1986527@gmail.com>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.design.jhbrowser.file.interfaces;

public interface ISDCardListener extends IBroadCastListener {

    /**
     * Broadcast Action: External media was removed from SD card slot, but mount point was not unmounted.
     * 广播行为：外部存储从SD卡插槽，但挂载点没有挂载
     */
    void onSDCardBadRemoval();

    /**
     * Broadcast Action: The "Media Button" was pressed.
     * 按下媒体按钮
     */
    void onSDCardButton();

    /**
     * Broadcast Action: External media is present,
     * and being disk-checked The path to the mount point
     * for the checking media is contained in the Intent.mData field.
     * 外部媒体存在,磁盘检查挂载点的路径中包含的检查媒体意图。mData字段。
     */
    void onSDCardChecking();

    /**
     * Broadcast Action: User has expressed the desire to remove the external storage media.
     * 用户表示移除外部存储测量的欲望
     */
    void onSDCardEject();

    /**
     * Broadcast Action: External media is present and mounted at its mount point.
     * 外部媒体存在和安装在挂载点
     */
    void onSDCardMounted();

    /**
     * Broadcast Action: External media is present, but is using an incompatible fs
     * (or is blank) The path to the mount point for the checking media is contained in the Intent.mData field.
     * 外部媒体存在,而是使用一个不兼容的fs(或者是空白)挂载点的路径中包含的检查媒体意图。mData领域
     */
    void onSDCardNoFS();

    /**
     * Broadcast Action: External media has been removed.
     * 外部媒体已被删除
     */
    void onSDCardRemoved();

    /**
     * Broadcast Action: The media scanner has finished scanning a directory.
     * 扫描仪完成扫描一个目录
     */
    void onSDCardScannerFinshed();

    /**
     * Broadcast Action: Request the media scanner to scan a file and add it to the media database.
     * 请求媒体扫描仪扫描一个文件,并将它添加到媒体数据库
     */
    void onSDCardScannerScaning();

    /**
     * Broadcast Action: The media scanner has started scanning a directory.
     * 媒体扫描仪已经开始扫描一个目录
     */
    void onSDCardScannerStarted();

    /**
     * Broadcast Action: External media is unmounted because it is being shared via USB mass storage.
     * 外部媒体是卸载的,因为它是通过USB大容量存储共享
     */
    void onSDCardShared();

    /**
     * Broadcast Action: External media is present but cannot be mounted.
     * 外部媒体存在但不能安装
     */
    void onSDCardUnMountable();

    /**
     * Broadcast Action: External media is present, but not mounted at its mount point.
     * 外部媒体存在,但不是未安装在挂载点
     */
    void onSDCardUnMounted();
}
