package models.common;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReportDataItem {
    private final String value;
    private final int count;
}
