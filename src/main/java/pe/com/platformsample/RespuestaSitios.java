package pe.com.platformsample;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.jlan.server.filesys.FileExistsException;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.model.Repository;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.cmr.site.SiteInfo;
import org.alfresco.service.cmr.site.SiteService;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.ibm.icu.text.SimpleDateFormat;

public class RespuestaSitios extends DeclarativeWebScript {

	private static final StoreRef STORE_REF = StoreRef.STORE_REF_WORKSPACE_SPACESSTORE;
	private static final String BASE_PATH = "workspace/SpacesStore/Company Home";

	private SiteService siteService;
	private PersonService personService;
	private NodeService nodeService;
	private FileFolderService fileFolderService;
	private ContentService contentService;
	private String rutaReporte;
	private String fileName;
	private Repository repository;
	
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileFolderService(FileFolderService fileFolderService) {
		this.fileFolderService = fileFolderService;
	}

	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

	public void setRutaReporte(String rutaReporte) {
		this.rutaReporte = rutaReporte;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
	
	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		// TODO Auto-generated method stub
	
		Map<String, Object> model = new HashMap<String, Object>();
	
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Sitios con Miembros");
		createheader(sheet, workbook);
		CellStyle centeredStyle = workbook.createCellStyle();
		centeredStyle.setAlignment(HorizontalAlignment.CENTER);
		String sitio = req.getParameter("sitio");
		int rowNum = 2;
		try {
			// List<Map<String, Object>> sitiosList = new ArrayList<>();

			if (!sitio.equals("0")) {
				SiteInfo sitesd = siteService.getSite(sitio);
				String siteId = sitesd.getShortName();
				String siteName = sitesd.getTitle();
				Map<String, String> siteMembers = siteService.listMembers(sitesd.getShortName(), null, null, 0);
				for (Map.Entry<String, String> memberEntry : siteMembers.entrySet()) {
					String memberId = memberEntry.getKey();
					String role = memberEntry.getValue();

					NodeRef personNodeRef = personService.getPerson(memberId);
					if (personNodeRef != null) {
						String firtsName = (String) nodeService.getProperty(personNodeRef, ContentModel.PROP_FIRSTNAME);
						String lastName = (String) nodeService.getProperty(personNodeRef, ContentModel.PROP_LASTNAME);
						String email = (String) nodeService.getProperty(personNodeRef, ContentModel.PROP_EMAIL);
						Row row = sheet.createRow(rowNum++);
						row.createCell(0).setCellValue(siteId);
						row.createCell(1).setCellValue(siteName);
						row.createCell(2).setCellValue(memberId);
						row.createCell(3).setCellValue(role);
						row.createCell(4).setCellValue(firtsName + " " + lastName);
						row.createCell(5).setCellValue(email);
						// Aplicar el estilo centrado a todas las celdas de la fila
						for (int i = 0; i < 6; i++) {
							row.getCell(i).setCellStyle(centeredStyle);
							row.getCell(i).getCellStyle().setBorderTop(BorderStyle.THIN);
							row.getCell(i).getCellStyle().setBorderBottom(BorderStyle.THIN);
							row.getCell(i).getCellStyle().setBorderLeft(BorderStyle.THIN);
							row.getCell(i).getCellStyle().setBorderRight(BorderStyle.THIN);

						}

					}
				}
				widthcolumns(sheet);
			}else{
				List<SiteInfo> sites = siteService.listSites(null, null, 0);
				for (SiteInfo siteInfo : sites) {
					/*
					 * Map<String, Object> sitioMap = new HashMap<>(); sitioMap.put("siteId",
					 * siteInfo.getShortName()); sitioMap.put("siteName", siteInfo.getTitle());
					 */
					String siteId = siteInfo.getShortName();
					String siteName = siteInfo.getTitle();
					

					Map<String, String> siteMembers = siteService.listMembers(siteInfo.getShortName(), null, null, 0);
					for (Map.Entry<String, String> memberEntry : siteMembers.entrySet()) {
						String memberId = memberEntry.getKey();
						String role = memberEntry.getValue();

						NodeRef personNodeRef = personService.getPerson(memberId);
						if (personNodeRef != null) {
							String firtsName = (String) nodeService.getProperty(personNodeRef,
									ContentModel.PROP_FIRSTNAME);
							String lastName = (String) nodeService.getProperty(personNodeRef,
									ContentModel.PROP_LASTNAME);
							String email = (String) nodeService.getProperty(personNodeRef, ContentModel.PROP_EMAIL);
							Row row = sheet.createRow(rowNum++);
							row.createCell(0).setCellValue(siteId);
							row.createCell(1).setCellValue(siteName);
							row.createCell(2).setCellValue(memberId);
							row.createCell(3).setCellValue(role);
							row.createCell(4).setCellValue(firtsName + " " + lastName);
							row.createCell(5).setCellValue(email);
							// Aplicar el estilo centrado a todas las celdas de la fila
							for (int i = 0; i < 6; i++) {
								row.getCell(i).setCellStyle(centeredStyle);
								row.getCell(i).getCellStyle().setBorderTop(BorderStyle.THIN);
								row.getCell(i).getCellStyle().setBorderBottom(BorderStyle.THIN);
								row.getCell(i).getCellStyle().setBorderLeft(BorderStyle.THIN);
								row.getCell(i).getCellStyle().setBorderRight(BorderStyle.THIN);

							}

						}
					}
					widthcolumns(sheet);

				}
				
			}

			// Obtén la fecha y hora actual
			Date fechaActual = new Date();
			// Define el formato que deseas para la fecha y hora
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			// Aplica el formato a la fecha y hora actual
			String fechaHoraFormateada = formato.format(fechaActual);

			// Guarda el libro de trabajo Excel en un flujo de salida
			// (ByteArrayOutputStream)
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			workbook.close();
			// Obtiene un InputStream a partir del ByteArrayOutputStream
			InputStream excelInputStream = new ByteArrayInputStream(outputStream.toByteArray());

		
			String fileNameend = fileName + " " + fechaHoraFormateada + ".xlsx";
			upload(excelInputStream,getNodeByPath(rutaReporte), fileNameend);

			excelInputStream.close();
			model.put("route", rutaReporte + fileNameend);
			
			
		} catch (Exception e) {
			model.put("error", e.getMessage());
			status.setCode(Status.STATUS_INTERNAL_SERVER_ERROR);
		}

		return model;
		
	}

	public void upload(InputStream is, NodeRef parent, String fileName) throws FileExistsException {
		NodeRef newNode = fileFolderService.create(parent, fileName, ContentModel.TYPE_CONTENT).getNodeRef();

		if (!nodeService.hasAspect(newNode, ContentModel.ASPECT_VERSIONABLE))
			nodeService.addAspect(newNode, ContentModel.ASPECT_VERSIONABLE, null);

		ContentWriter writer = contentService.getWriter(newNode, ContentModel.PROP_CONTENT, true);
		writer.guessMimetype(fileName);
		writer.guessEncoding();
		writer.putContent(is);
		

	}

	void createheader(Sheet sheet, Workbook workbook) {
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		Row sitesHeaderRow = sheet.createRow(0);
		Cell sitesCell = sitesHeaderRow.createCell(0);
		sitesCell.setCellValue("Sitios con miembros");

		// Crear estilo de celda centrada para los encabezados
		CellStyle headerCenteredStyle = workbook.createCellStyle();
		headerCenteredStyle.setAlignment(HorizontalAlignment.CENTER);

		// Especificar el color de fondo para los encabezados
		headerCenteredStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex()); // Puedes elegir cualquier
																							// color de fondo que desees
		headerCenteredStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		// Configurar bordes para los encabezados
		headerCenteredStyle.setBorderTop(BorderStyle.THIN);
		headerCenteredStyle.setBorderBottom(BorderStyle.THIN);
		headerCenteredStyle.setBorderLeft(BorderStyle.THIN);
		headerCenteredStyle.setBorderRight(BorderStyle.THIN);

		// Encabezados para "id", "role", "fullName" y "email"
		Row memberHeaderRow = sheet.createRow(1);
		String[] memberHeaders = { "Id del sitio", "Nombre del sitio", "Id", "Rol", "Nombre completo", "Correo electrónico" };
		for (int i = 0; i < memberHeaders.length; i++) {
			Cell cell = memberHeaderRow.createCell(i);
			cell.setCellValue(memberHeaders[i]);
			cell.setCellStyle(headerCenteredStyle); // Aplicar estilo centrado a las celdas de encabezados
		}

		// Aplicar estilo centrado a las celdas de encabezados
		sitesCell.setCellStyle(headerCenteredStyle);

	}

	void widthcolumns(Sheet sheet) {
		// Ajustar automáticamente el ancho de las columnas
		for (int i = 0; i < 6; i++) {
			sheet.autoSizeColumn(i);
		}

	}
	
	
	public NodeRef getNodeByPath(String nodePath) {
		if (isBlank(nodePath))
			return null;

		nodePath = BASE_PATH + "/" + nodePath;
		return repository.findNodeRef("path", formatPath(nodePath).split("/"));
	}

	public static boolean isBlank(String ptext) {
		return ptext == null || ptext.trim().length() == 0 || ptext.trim().toLowerCase().equals("null");
	}

	public String formatPath(String path) {
		if (path.trim().startsWith("/"))
			path = path.trim().substring(1, path.length());

		if (path.trim().endsWith("/"))
			path = path.trim().substring(0, path.length() - 1);

		return path;
	}
	

}
