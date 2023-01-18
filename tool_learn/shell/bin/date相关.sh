# shell中的日期循环 实现周期性质的补数据
start_day=20221002
end_day=20230102
dt=$start_day
while [[ $dt < `date -d "+1 day $end_day" +%Y%m%d` ]]
do
    echo $dt
    sh XX.sh $dt
    if [ $? -ne 0 ]; then
    echo "ERROR: $dt分区数据不正常 退出"
        exit 1
    else
        echo "INFO:当天分区数据正常 继续"
    fi
    dt=`date -d "+1 day $dt" +%Y%m%d`
done

