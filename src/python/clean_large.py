#!/usr/bin/env python3
import os
import shutil
import argparse

# 删除磁盘文件夹大文件
# 使用方法：python clean_large.py /path/to/dir --size 512
# 其中size以MB为单位
def get_folder_size(path):
    """
    递归计算目录总大小（字节）。
    """
    total = 0
    for root, dirs, files in os.walk(path, topdown=True):
        for fname in files:
            try:
                fp = os.path.join(root, fname)
                total += os.path.getsize(fp)
            except OSError:
                pass
    return total

def find_large_items(root_path, size_threshold):
    """
    在 root_path 下找出所有超过 size_threshold 的文件或文件夹。
    返回 [(path, size_in_bytes), ...] 列表。
    """
    large_items = []
    for entry in os.scandir(root_path):
        try:
            full_path = entry.path
            if entry.is_file(follow_symlinks=False):
                size = os.path.getsize(full_path)
            elif entry.is_dir(follow_symlinks=False):
                size = get_folder_size(full_path)
            else:
                continue
            if size >= size_threshold:
                large_items.append((full_path, size))
        except OSError:
            continue
    return large_items

def confirm_and_delete(items):
    """
    打印待删除列表，提示用户确认，确认后执行删除。
    """
    print("以下项目将被删除：")
    for path, size in items:
        print(f"  {path} ({size/1024/1024:.2f} MB)")
    ans = input("确认删除这些项目？输入 y 确认，其他键取消：")
    if ans.lower() == 'y':
        for path, _ in items:
            try:
                if os.path.isfile(path):
                    os.remove(path)
                else:
                    shutil.rmtree(path)
                print(f"已删除: {path}")
            except Exception as e:
                print(f"删除失败: {path}，原因：{e}")
    else:
        print("已取消删除。")

def main():
    parser = argparse.ArgumentParser(
        description="删除指定目录下过大的文件或文件夹，并要求确认。"
    )
    parser.add_argument("path", help="要扫描的目录路径")
    parser.add_argument(
        "-s", "--size",
        type=float,
        default=1024.0,
        help="大小阈值（MB），默认 1024 MB"
    )
    args = parser.parse_args()

    # 将 MB 转换为字节
    threshold_bytes = args.size * 1024 * 1024
    items = find_large_items(args.path, threshold_bytes)
    if not items:
        print("未找到超过阈值的大文件或文件夹。")
    else:
        confirm_and_delete(items)

if __name__ == "__main__":
    main()
