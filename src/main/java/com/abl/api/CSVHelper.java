package com.abl.api;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"Excode","Tranno","Currency","Amount","Entered Date","Remitter","Beneficiary","Bene A/C","Bank Name","Bank Code","Branch Name","Branch Code"};

    public static boolean hasCSVFormat(MultipartFile file) {
        if (TYPE.equals(file.getContentType())
    		|| file.getContentType().equals("application/vnd.ms-excel")) {
         return true;
        }
        return false;
    }

    public static List<APIModel> csvToAPIModels(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
          CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<APIModel> apiModelList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                APIModel apiModel = new APIModel(
                    Integer.parseInt(csvRecord.get("Excode").replace("\"", "")),
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
                apiModelList.add(apiModel);
            }
         return apiModelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream apiModelToCSV(List<APIModel> apiModelList) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.NON_NUMERIC);
    
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
            CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
          for (APIModel apiModel : apiModelList) {
            List<Object> data = Arrays.asList(
                  //String.valueOf(apiModel.getId()),
                    apiModel.getTranNo(),
                    "CRED",
                    //apiModel.getExCode(),
                    apiModel.getEnteredDate(),
                    apiModel.getCurrency(),
                    apiModel.getAmount(),
                    apiModel.getBeneficiary(),
                    "exchane code",
                    apiModel.getBankName(),
                    apiModel.getBranchName(),
                    null,
                    apiModel.getBeneficiaryAccount(),
                    apiModel.getRemitter(),
                    null,
                    null,
                    //apiModel.getBankCode(),
                    apiModel.getBranchCode(),
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
