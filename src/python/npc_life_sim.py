# npc_life_sim.py
# 简单模拟“NPC 生活”：周末外卖、工作日作息、节假日抢票吐槽、周三催打游戏等
import datetime
import random

# 配置区（可按需修改）
SIM_DAYS = 14  # 从今天开始模拟多少天
WORK_START = datetime.time(8, 50)   # 上班时间
FREE_BREAKFAST = datetime.time(9, 0)  # 免费早餐时间
LUNCH_DONE = datetime.time(12, 30)  # 吃完饭并遛弯时间
WED_REMIND = datetime.time(18, 30)  # 周三同门催你打游戏的时间（假设此时你还没下班）
SAT_LAUNDRY = datetime.time(15, 0)  # 周六固定洗衣服时间
EVENING_MATCH = "英超比赛"  # 晚上活动描述

# 假日配置：使用 yyyy-mm-dd 字符串列出具体放假日期（示例包含五一与国庆）
# 注意：春节每年日期不同，若要包含请把具体日期也加入此列表，例如 "2026-02-10"
HOLIDAYS = {
    "2026-05-01",  # 五一（示例）
    "2026-10-01",  # 国庆（示例）
    # 如果你想包含 2026 年的春节（示例假设日期，实际每年不同），可以添加：
    # "2026-02-??"  # 请替换为真实日期或手动添加
}
# 提示：如果你希望程序自动把每年固定的月-日视为假日（如每年5月1日），可以改用 month-day 的比较。

# 周末午餐选项
WEEKEND_LUNCH_OPTIONS = ["老乡鸡外卖", "乡村基外卖"]

def is_weekend(dt):
    return dt.weekday() >= 5  # 5=Saturday,6=Sunday

def is_holiday(dt):
    s = dt.strftime("%Y-%m-%d")
    return s in HOLIDAYS

def simulate_day(dt):
    weekday = dt.weekday()  # 0=Mon ... 6=Sun
    date_str = dt.strftime("%Y-%m-%d (%a)")
    print(f"=== {date_str} ===")

    # 节假日优先提示
    if is_holiday(dt):
        print("今天是国家法定假日！开始检查出行计划...")
        print("尝试在 12306 抢票：抢不到 -> 秒候补中...")
        print("12306司马了")
        # 假日里也可能是周末或工作日，下面继续按日期打印日常活动
    # 工作日作息
    if not is_weekend(dt):
        # 假设工作日
        print(f"{WORK_START.strftime('%H:%M')} - 准时上班。")
        # 9:00 吃免费早餐
        print(f"{FREE_BREAKFAST.strftime('%H:%M')} - 吃公司/单位提供的免费早餐。")
        # 12:30 吃完饭并遛弯
        print(f"{LUNCH_DONE.strftime('%H:%M')} - 午饭结束，出门遛弯。")
        # 周三傍晚的催游戏消息
        if weekday == 2:  # 0 Mon, 1 Tue, 2 Wed
            # 假设你18:30还没下班 -> 收到催打游戏
            print(f"{WED_REMIND.strftime('%H:%M')} - 收到研究生同门发上号，催我去打游戏（可惜还没下班）。")
    else:
        # 周末活动
        # 早上点面包外卖
        print("早上 - 点一个面包外卖当早餐（周六/周日专属）。")
        # 中午点老乡鸡或乡村基外卖（随机）
        lunch = random.choice(WEEKEND_LUNCH_OPTIONS)
        print(f"中午 - 点外卖：{lunch}。")
        # 晚上点遇见小面外卖
        print("晚上 - 点遇见小面外卖当晚餐。")
        # 周六下午固定洗衣服
        if weekday == 5:  # Saturday
            print(f"{SAT_LAUNDRY.strftime('%H:%M')} - 固定时间洗衣服。")
        # 周末晚上看英超并锻炼
        print("晚上 - 看英超比赛并顺便锻炼身体。")

    print("")  # 空行分隔

def simulate_period(start_date=None, days=SIM_DAYS):
    if start_date is None:
        start = datetime.date.today()
    else:
        start = start_date
    for i in range(days):
        d = start + datetime.timedelta(days=i)
        simulate_day(d)

if __name__ == "__main__":
    # 直接运行：模拟从今天开始 SIM_DAYS 天
    print("开始模拟你的 NPC 生活（简单版）\n")
    simulate_period()
    print("模拟结束。你可以修改脚本中的配置来调整假日、模拟天数或时间点。")
