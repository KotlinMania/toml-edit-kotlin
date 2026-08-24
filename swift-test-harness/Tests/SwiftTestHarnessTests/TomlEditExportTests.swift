#if canImport(Testing)
import Testing
import TomlEdit

@Suite("TomlEdit Swift Export Smoke Tests")
struct TomlEditExportTests {
    @Test("Swift module loads and basic assertion passes")
    func swiftModuleLoads() {
        let table = Table.Companion.shared.new()
        #expect(table.isEmpty())
        let doc = Document.Companion.shared.new()
        #expect(doc.asTable() != nil)
    }
}
#elseif canImport(XCTest)
import XCTest
import TomlEdit

final class TomlEditExportTests: XCTestCase {
    func testSwiftModuleLoads() throws {
        let table = Table.Companion.shared.new()
        XCTAssertTrue(table.isEmpty())
        let doc = Document.Companion.shared.new()
        XCTAssertNotNil(doc.asTable())
    }
}
#endif

