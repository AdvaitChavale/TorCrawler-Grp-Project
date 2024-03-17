import PyPDF2

def fill_pdf_template(template_path, output_path, data):
    with open(template_path, 'rb') as template_file:
        pdf_reader = PyPDF2.PdfFileReader(template_file)
        pdf_writer = PyPDF2.PdfFileWriter()

        for page_num in range(pdf_reader.numPages):
            page = pdf_reader.getPage(page_num)
            # Modify the page content based on the extracted data
            # For demonstration, let's add the extracted title to the PDF
            page.mergePage(PyPDF2.PdfFileReader(title).getPage(0))
            pdf_writer.addPage(page)

        with open(output_path, 'wb') as output_file:
            pdf_writer.write(output_file)

# # Step 3: Fill in the PDF template with the extracted data
# data_to_fill_in = {
#     'Title': title,
#     # Add more data fields as needed
# }

# fill_pdf_template(pdf_template_path, pdf_output_path, data_to_fill_in)