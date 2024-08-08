package com.geeke.utils;

import com.geeke.salaryData.entity.SalaryData;
import com.geeke.salaryData.entity.SalaryTemp;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.BLANK;

public class SalaryCountUtils {
    public static boolean isRowNotEmpty(Row row) {
        for (int c = 0; c < 3; c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != BLANK) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据具体的薪资信息和专项附加的薪资项目计算出专项附加合计
     * @param salaryData
     * @param itemA7s
     * @return
     */
    public static BigDecimal getSpecialAdditional(SalaryData salaryData, List<SalaryTemp> itemA7s){
        //专项附加合计
        BigDecimal specialAdditional=BigDecimal.ZERO;
        if (itemA7s==null||itemA7s.size()<1){
            return specialAdditional;
        }
        for (SalaryTemp itemA7 : itemA7s) {
            //获取到这个项目对应的def几
            String itemSerialNewId = itemA7.getSaItemDesc();
            //取到实际值
            BigDecimal dataStr = null;
            try {
                Field declaredField = SalaryData.class.getDeclaredField("def" + itemSerialNewId);
                dataStr = (BigDecimal)declaredField.get(salaryData);
            } catch (Exception e) {
                e.printStackTrace();
            }
            BigDecimal data=(dataStr!=null?dataStr:new BigDecimal(0));
            //算合计值
            specialAdditional=specialAdditional.add(data);
        }
        return specialAdditional;
    }
}
