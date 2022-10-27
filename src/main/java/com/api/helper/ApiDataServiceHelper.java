package com.api.helper;

import com.api.model.ApiDataModel;
import org.apache.commons.csv.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ApiDataServiceHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"Excode","Tranno","Currency","Amount","Entered Date","Remitter","Beneficiary","Bene A/C","Bank Name","Bank Code","Branch Name","Branch Code"};
    public static boolean hasCSVFormat(MultipartFile file) {
        if (TYPE.equals(file.getContentType())
    		|| file.getContentType().equals("application/vnd.ms-excel")) {
         return true;
        }
        return false;
    }

    public static List<ApiDataModel> csvToAPIModels(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
          CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<ApiDataModel> apiDataModelList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                ApiDataModel apiDataModel = new ApiDataModel(
                    csvRecord.get("Excode").replace("\"", ""),
                    csvRecord.get("Tranno").replace("\"", ""),
                    csvRecord.get("Currency").replace("\"", ""),
                    Double.parseDouble(csvRecord.get("Amount").replace("\"", "")),
                    csvRecord.get("Entered Date").replace("\"", ""),
                    csvRecord.get("Remitter").replace("\"", ""),
                    csvRecord.get("Beneficiary").replace("\"", ""),
                    csvRecord.get("Bene A/C").replace("\"", ""),
                    csvRecord.get("Bank Name").replace("\"", ""),
                    csvRecord.get("Bank Code").replace("\"", ""),
                    csvRecord.get("Branch Name").replace("\"", ""),
                    csvRecord.get("Branch Code").replace("\"", ""));
                apiDataModelList.add(apiDataModel);
            }
         return apiDataModelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream apiModelToCSV(List<ApiDataModel> apiDataModelList) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.NON_NUMERIC);
    
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
            CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
          for (ApiDataModel apiDataModel : apiDataModelList) {
            List<Object> data = Arrays.asList(
                    apiDataModel.getTranNo(),
                    "CRED",
                    apiDataModel.getEnteredDate(),
                    apiDataModel.getCurrency(),
                    apiDataModel.getAmount(),
                    apiDataModel.getRemitter(),
                    apiDataModel.getExCode(),
                    apiDataModel.getBankName(),
                    apiDataModel.getBranchName(),
                    null,
                    apiDataModel.getBeneficiaryAccount(),
                    apiDataModel.getBeneficiary(),
                    null,
                    null,
                    //apiModel.getBankCode(),
                    "4006",
                    //apiModel.getBranchCode(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                );
            csvPrinter.printRecord(data);
          }
    
          csvPrinter.flush();
          return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
          throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
