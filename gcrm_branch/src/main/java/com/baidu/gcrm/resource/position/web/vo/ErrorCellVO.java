package com.baidu.gcrm.resource.position.web.vo;

public class ErrorCellVO {
    
    private int row;
    private int column;
    
    private ErrorCellVO otherCell;
    
    public ErrorCellVO(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getColumn() {
        return column;
    }
    public void setColumn(int column) {
        this.column = column;
    }

    public ErrorCellVO getOtherCell() {
        return otherCell;
    }

    public void setOtherCell(ErrorCellVO otherCell) {
        this.otherCell = otherCell;
    }
    
    
    
}
