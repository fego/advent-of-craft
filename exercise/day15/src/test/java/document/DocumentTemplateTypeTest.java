package document;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class DocumentTemplateTypeTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
            DEER, INDIVIDUAL_PROSPECT,DEERPP
            DEER, LEGAL_PROSPECT,DEERPM
            AUTP,INDIVIDUAL_PROSPECT,AUTP
            AUTM,LEGAL_PROSPECT,AUTM
            SPEC,LEGAL_PROSPECT,SPEC
            GLPP,INDIVIDUAL_PROSPECT,GLPP
            GLPM,LEGAL_PROSPECT,GLPM
            """)
    void map_document_template_type(String documentType, String recordType, DocumentTemplateType expectedDocument) {
        DocumentTemplateType documentTemplateType = DocumentTemplateType.fromDocumentTypeAndRecordType(documentType, recordType);
        Assertions.assertThat(documentTemplateType).isEqualTo(expectedDocument);
    }

    @Test
    void no_document_template_type_mapping() {
        Exception exception = Assertions.catchException(() -> DocumentTemplateType.fromDocumentTypeAndRecordType("INDIVIDUAL_PROSPECT", "GLPM"));
        Assertions.assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
    }
}
