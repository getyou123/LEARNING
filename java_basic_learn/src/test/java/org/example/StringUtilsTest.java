package org.example;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * 测试StringUtilsTest
 */

public class StringUtilsTest {
    @Test
    public void test001() {

        // 把字符串缩短到某种长度，按照某字符串结尾
        // 缩短到某长度,用...结尾.其实就是(substring(str, 0, max-3) + "...")
        System.out.println(StringUtils.abbreviate("abcdefg", 6));
        // ---"abc..."

        //字符串结尾的后缀是否与你要结尾的后缀匹配，若不匹配则添加后缀
        System.out.println(StringUtils.appendIfMissing("abc", "xyz"));
        //---"abcxyz"
        System.out.println(StringUtils.appendIfMissingIgnoreCase("abcXYZ", "xyz"));
        //---"abcXYZ"

        //首字母大小写转换
        System.out.println(StringUtils.capitalize("cat"));
        //---"Cat"
        System.out.println(StringUtils.uncapitalize("Cat"));
        //---"cat"

        //字符串扩充至指定大小且居中（若扩充大小少于原字符大小则返回原字符，若扩充大小为负数则为0计算 ）
        System.out.println(StringUtils.center("abcd", 2));
        //--- "abcd"
        System.out.println(StringUtils.center("ab", -1));
        //--- "ab" 负数按照0计算，0小于字符串长度 返回原字符串
        System.out.println(StringUtils.center("ab", 4));
        //---" ab "
        System.out.println(StringUtils.center("a", 4, "yz"));
        //---"yayz"
        System.out.println(StringUtils.center("abc", 7, ""));
        //---"  abc  "

        //去除字符串中的"\n", "\r", or "\r\n"
        System.out.println(StringUtils.chomp("abc\r\n"));
        //---"abc"

        //判断一字符串是否包含另一字符串
        System.out.println(StringUtils.contains("abc", "z"));
        //---false
        System.out.println(StringUtils.containsIgnoreCase("abc", "A"));
        //---true

        // 是否包含 空格 制表符 tab
        System.out.println(StringUtils.containsWhitespace("  saf"));

        //统计一字符串在另一字符串中出现次数
        System.out.println(StringUtils.countMatches("abba", "a"));
        //---2

        //删除字符串中的所有空格
        System.out.println(StringUtils.deleteWhitespace("   ab  c  "));
        //---"abc"

        //比较两字符串，返回不同之处。确切的说是返回第二个参数中与第一个参数所不同的字符串
        System.out.println(StringUtils.difference("abcde", "abxyz"));
        //---"xyz"

        //检查字符串结尾后缀是否匹配
        System.out.println(StringUtils.endsWith("abcdef", "def"));
        //---true
        System.out.println(StringUtils.endsWithIgnoreCase("ABCDEF", "def"));
        //---true
        System.out.println(StringUtils.endsWithAny("abcxyz", new String[]{null, "xyz", "abc"}));
        //---true

        //检查起始字符串是否匹配
        System.out.println(StringUtils.startsWith("abcdef", "abc"));
        //---true
        System.out.println(StringUtils.startsWithIgnoreCase("ABCDEF", "abc"));
        //---true
        System.out.println(StringUtils.startsWithAny("abcxyz", new String[]{null, "xyz", "abc"}));
        //---true

        //判断两字符串是否相同
        System.out.println(StringUtils.equals("abc", "abc"));
        //---true
        System.out.println(StringUtils.equalsIgnoreCase("abc", "ABC"));
        //---true
        System.out.println(StringUtils.equals("abc", null));
        //---false
        System.out.println(StringUtils.equals(null, null));
        //---true
        System.out.println(StringUtils.equals(null, "abc"));
        //---false


        //比较字符串数组内的所有元素的字符序列，起始一致则返回一致的字符串，若无则返回""
        System.out.println(StringUtils.getCommonPrefix(new String[]{"abcde", "abxyz"}));
        //---"ab"

        //正向查找字符在字符串中第一次出现的位置
        System.out.println(StringUtils.indexOf("aabaabaa", "b"));
        //---2
        System.out.println(StringUtils.indexOf("aabaabaa", "b", 3));
        //---5(从角标3后查找)
        System.out.println(StringUtils.ordinalIndexOf("aabaabaa", "a", 3));
        //---3(查找第n次出现的位置)
        System.out.println(StringUtils.ordinalIndexOf("aabaabaa", "b", 3));
        //--- -1 未找到

        //反向查找字符串第一次出现的位置
        System.out.println(StringUtils.lastIndexOf("aabaabaa", 'b'));
        //---5
        System.out.println(StringUtils.lastIndexOf("aabaabaa", 'b', 4));
        //---2 从位置4开始往前找
        System.out.println(StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 2));
        //---1 从后往前找第二次 ab 的位置

        //判断字符串大写、小写
        System.out.println(StringUtils.isAllUpperCase("ABC"));
        //---true
        System.out.println(StringUtils.isAllLowerCase("abC"));
        //---false

        //判断是否为空(注：isBlank与isEmpty 区别)
        System.out.println(StringUtils.isBlank(null));
        //---true
        System.out.println(StringUtils.isBlank(""));
        //---true
        System.out.println(StringUtils.isBlank(" "));
        //---true
        System.out.println(StringUtils.isNoneBlank(" ", "bar"));
        //---false
        System.out.println(StringUtils.isEmpty(null));
        //---true
        System.out.println(StringUtils.isEmpty(""));
        //---true
        System.out.println(StringUtils.isEmpty(" "));
        //---false
        System.out.println(StringUtils.isNoneEmpty(" ", "bar"));
        //---true
        System.out.println(StringUtils.defaultIfEmpty(null, "default"));
        // -- default



        //判断字符串数字
        System.out.println(StringUtils.isNumeric("123"));
        //---true
        System.out.println(StringUtils.isNumeric("12 3"));
        //---false
        System.out.println(StringUtils.isNumeric("12.3"));
        //---false (不识别运算符号、小数点、空格……)
        System.out.println(StringUtils.isNumericSpace("12 3"));
        //---true
        System.out.println(StringUtils.join(new  String[]{"a", "b", "c"}, ';'));
        //---"1;2;3"

        // 大小写转化
        System.out.println(StringUtils.upperCase("aBc"));
        //---"ABC"
        System.out.println(StringUtils.lowerCase("aBc"));
        //---"abc"
        System.out.println(StringUtils.swapCase("The dog has a BONE"));
        //---"tHE DOG HAS A bone"


        //替换字符串内容……（replacePattern、replceOnce）
        System.out.println(StringUtils.replace("aba", "a", "z"));
        //---"zbz"
        System.out.println(StringUtils.overlay("abcdef", "zz", 2, 4));
        //---"abzzef"(指定区域)
        System.out.println(StringUtils.replaceEach("abcde",
                new String[]{"ab", "d"},
                new String[]{"w", "t"}));
        //---"wcte"(多组指定替换ab->w，d->t)

        //重复字符
        System.out.println(StringUtils.repeat('e', 3));
        //---"eee"

        //反转字符串
        System.out.println(StringUtils.reverse("bat"));
        //---"tab"

        //删除某字符
        System.out.println(StringUtils.remove("queued", 'u'));
        //---"qeed"

        //分割字符串
        System.out.println(StringUtils.join(StringUtils.split("a..b.c", '.'),";"));
        //---["a", "b", "c"]
        System.out.println(StringUtils.join(StringUtils.split("ab:cd:ef", ":", 2),"#"));
        //---["ab", "cd:ef"] 从头开启切然后最长两段
        System.out.println(StringUtils.join(StringUtils.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 2),"#"));
        //---["ab", "cd-!-ef"]
        System.out.println(StringUtils.join(StringUtils.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-"),"#"));
        //-["ab"," ","cd","ef"]


        //去除首尾空格，类似trim……（stripStart、stripEnd、stripAll、stripAccents）
        System.out.println(StringUtils.strip(" ab c "));
        //---"ab c"
        System.out.println(StringUtils.stripToNull(null));
        //---null
        System.out.println(StringUtils.stripToEmpty(null));
        //---""


        //截取字符串
        System.out.println(StringUtils.substring("abcd", 2));
        //---"cd"
        System.out.println(StringUtils.substring("abcdef", 2, 4));
        //---"cd"
        //left、right从左(右)开始截取n位字符
        System.out.println(StringUtils.left("abc", 2));
        //---"ab"
        System.out.println(StringUtils.right("abc", 2));
        //---"bc"
        //从第n位开始截取m位字符       n  m
        System.out.println(StringUtils.mid("abcdefg", 2, 4));
        //---"cdef"
        System.out.println(StringUtils.substringBefore("abcba", "b"));
        //---"a"
        System.out.println(StringUtils.substringBeforeLast("abcba", "b"));
        //---"abc"
        System.out.println(StringUtils.substringAfter("abcba", "b"));
        //---"cba"
        System.out.println(StringUtils.substringAfterLast("abcba", "b"));
        //---"a"
        System.out.println(StringUtils.substringBetween("tagabctag", "tag"));
        //---"abc"
        System.out.println(StringUtils.substringBetween("yabczyacz", "y", "z"));
        //---"abc"

        // left 在左侧填充
        System.out.println(StringUtils.leftPad("1234",7, 'X'));
        // -- XXX1234

        //  right 在右侧填充
        System.out.println(StringUtils.rightPad("1234",7, 'X'));
        // -- 1234XXX

    }
}
