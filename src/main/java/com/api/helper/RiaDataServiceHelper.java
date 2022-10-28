package com.api.helper;

import com.api.model.RiaDataModel;
import org.apache.commons.csv.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RiaDataServiceHelper {
    public static String TYPE = "text/csv";
    public static boolean hasCSVFormat(MultipartFile file) {
        if (TYPE.equals(file.getContentType())
                || file.getContentType().equals("application/vnd.ms-excel")) {
            return true;
        }
        return false;
    }

    public static List<RiaDataModel> csvToRiaDataModels(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<RiaDataModel> riaDataModelList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                RiaDataModel riaDataModel = new RiaDataModel(
                        csvRecord.get("pin").replace("\"", ""),
                        Double.parseDouble(csvRecord.get("beneamount").replace("\"", "")),
                        csvRecord.get("orderno").replace("\"", ""),
                        csvRecord.get("Remitter").replace("\"", ""),
                        Double.parseDouble(csvRecord.get("Incentive").replace("\"", "")),
                        csvRecord.get("sender_country").replace("\"", ""),
                        csvRecord.get("Bene Name").replace("\"", ""),
                        csvRecord.get("Bene Account No").replace("\"", ""),
                        csvRecord.get("T24 Status").replace("\"", ""),
                        csvRecord.get("Status").replace("\"", ""),
                        csvRecord.get("Paid Date").replace("\"", ""),
                        csvRecord.get("paidby").replace("\"", ""));
                riaDataModelList.add(riaDataModel);
            }
            return riaDataModelList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream riaDataModelsToCSV(List<RiaDataModel> riaDataModelList) {
        final CSVFormat format = CSVFormat.INFORMIX_UNLOAD.withDelimiter('|');

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            for (RiaDataModel riaDataModel : riaDataModelList) {
                List<Object> data = Arrays.asList(
                        "7040",
                        riaDataModel.getPin().trim(),
                        "BDT",
                        riaDataModel.getAmount(),
                        riaDataModel.getPaidDate().trim(),
                        riaDataModel.getBeneficiaryName().trim(),
                        riaDataModel.getRemitter().trim(),
                        riaDataModel.getBeneficiaryAccount().trim(),
                        "AGRANI BANK LTD.",
                        "11",
                        "Principal Br",
                        "4006",
                        null,
                        null,
                        null,
                        null,
                        null,
                        "0"
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
