import Testing
import TomlEdit

@Suite("TomlEdit Swift Export Suite")
struct TomlEditExportTests {
    @Test("Swift module loads and basic assertion passes")
    func swiftModuleLoads() {
        let table = Table.Companion.shared.new()
        #expect(table.isEmpty())
        let doc = Document.Companion.shared.new()
        #expect(doc.asTable() != nil)
    }
}

