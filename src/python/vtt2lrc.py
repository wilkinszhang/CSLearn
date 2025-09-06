#!/usr/bin/env python3
# vtt2lrc.py

"""
使用： python vtt2lrc.py "E:\download\RJ01436514\要不要為網咖女子做推活呢？＠音撫屋\MP3"
"""

import os
import re
import argparse

# 支持的音频扩展名集合，如果还有其他需要，可以自行补充
AUDIO_EXTS = {'.mp3', '.wav', '.flac', '.aac', '.m4a', '.ogg', '.opus'}

# 匹配 WebVTT 时间戳行：00:00:01.234 --> 00:00:04.567
TIMESTAMP_PATTERN = re.compile(
    r'(\d{2}):(\d{2}):(\d{2}\.\d{3})\s*-->\s*(\d{2}):(\d{2}):(\d{2}\.\d{3})'
)

def vtt_to_lrc(vtt_path: str, lrc_path: str):
    """
    将单个 .vtt 文件转换为 .lrc 文件。
    """
    with open(vtt_path, 'r', encoding='utf-8') as f:
        lines = f.readlines()

    lrc_lines = []
    i = 0
    while i < len(lines):
        line = lines[i].strip()
        m = TIMESTAMP_PATTERN.match(line)
        if m:
            # 提取起始时间
            hh, mm, ss_ms = m.group(1), m.group(2), m.group(3)
            hours = int(hh)
            minutes = hours * 60 + int(mm)
            sec_part, ms_part = ss_ms.split('.')
            seconds = int(sec_part)
            milliseconds = int(ms_part)
            # LRC 里只保留到百分之一秒
            centiseconds = round(milliseconds / 10)
            timestamp = f'[{minutes:02d}:{seconds:02d}.{centiseconds:02d}]'

            # 紧接着的文本行都属于这个 cue，直到空行或下一个时间戳
            j = i + 1
            while j < len(lines) and lines[j].strip() != '':
                text = lines[j].strip()
                # 跳过样式、注释等非正文行
                if not text.startswith(('NOTE', 'STYLE', 'REGION')):
                    lrc_lines.append(f'{timestamp}{text}')
                j += 1

            i = j
        else:
            i += 1

    # 写入 .lrc 文件
    with open(lrc_path, 'w', encoding='utf-8') as f:
        f.write('\n'.join(lrc_lines))
        f.write('\n')  # 保证文件末尾有换行

def process_directory(root_dir: str):
    """
    递归遍历 root_dir，将所有 .vtt 文件转换为同目录下同名 .lrc 文件。
    对于 *.mp3.vtt、*.wav.vtt 等双扩展名，会去掉音频扩展，生成更干净的 .lrc 名称。
    """
    for dirpath, _, filenames in os.walk(root_dir):
        for fn in filenames:
            if fn.lower().endswith('.vtt'):
                # 去掉 .vtt
                root1, _ = os.path.splitext(fn)
                # 再去掉可能的音频扩展
                root2, ext2 = os.path.splitext(root1)
                if ext2.lower() in AUDIO_EXTS:
                    base_name = root2
                else:
                    base_name = root1

                vtt_file = os.path.join(dirpath, fn)
                lrc_file = os.path.join(dirpath, base_name + '.lrc')
                print(f'Converting: {vtt_file} -> {lrc_file}')
                vtt_to_lrc(vtt_file, lrc_file)

def main():
    parser = argparse.ArgumentParser(description='批量将 .vtt 歌词转换为 .lrc 格式')
    parser.add_argument('directory', help='要处理的根目录')
    args = parser.parse_args()

    if not os.path.isdir(args.directory):
        parser.error(f"目录不存在: {args.directory}")

    process_directory(args.directory)

if __name__ == '__main__':
    main()