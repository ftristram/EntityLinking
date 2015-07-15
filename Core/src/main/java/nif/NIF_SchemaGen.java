package main.java.nif;
/* CVS $Id: $ */
 
import com.hp.hpl.jena.rdf.model.*;
 
/**
 * Vocabulary definitions from E:/Master Project/jena schemagen/nif-core_2.owl 
 * @author Auto-generated by schemagen on 16 Jun 2015 11:08 
 */
public class NIF_SchemaGen {
	/** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model m_model = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    /** <p>The ontology's owl:versionInfo as a string</p> */
    public static final String VERSION_INFO = "Versioning done on resource level. See https://github.com/NLP2RDF/ontologies/blob/master/nif-core/nif-core.ttl";
    
    /** <p>For each string you can include a snippet (e.g. 10-40 characters of text), 
     *  that occurs immediately after the subject string.</p>
     */
    public static final Property after = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#after" );
    
    /** <p>The string, which the URI is representing as an RDF Literal. Some use cases 
     *  require this property, as it is necessary for certain sparql queries.</p>
     */
    public static final Property anchorOf = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#anchorOf" );
    
    /** <p>see Towards Web-Scale Collaborative Knowledge Extraction http://svn.aksw.org/papers/2012/PeoplesWeb/public_preprint.pdf? 
     *  page 21 . Changelog: * 0.1.1 Fixed page number</p>
     */
    public static final Property annotation = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#annotation" );
    
    /** <p>For each string you can include a snippet (e.g. 10-40 characters of text), 
     *  that occurs immediately before the subject string.</p>
     */
    public static final Property before = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#before" );
    
    /** <p>The begin index of a character range as defined in http://tools.ietf.org/html/rfc5147#section-2.2.1 
     *  and http://tools.ietf.org/html/rfc5147#section-2.2.2, measured as the gap 
     *  between two characters, starting to count from 0 (the position before the 
     *  first character of a text). Example: Index "2" is the postion between "Mr" 
     *  and "." in "Mr. Sandman". Note: RFC 5147 is re-used for the definition of 
     *  character ranges. RFC 5147 is assuming a text/plain MIME type. NIF builds 
     *  upon Unicode and is content agnostic. Requirement (1): This property has the 
     *  same value the "Character position" of RFC 5147 and it MUST therefore be castable 
     *  to xsd:nonNegativeInteger, i.e. it MUST not have negative values. Requirement 
     *  (2): The index of the subject string MUST be calculated relative to the nif:referenceContext 
     *  of the subject. If available, this is the rdf:Literal of the nif:isString 
     *  property. Changelog: * 1.0.0: Introduced stable version. * 1.0.1: merged val+inf, 
     *  added range</p>
     */
    public static final Property beginIndex = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#beginIndex" );
    
    /** <p>This property should be used to express that one Context is contained in another 
     *  Context, e.g. several sentences of a document are modelled indivudally and 
     *  refer to the broader context of the whole document.</p>
     */
    public static final Property broaderContext = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#broaderContext" );
    
    /** <p>The confidence of an annotation as decimal between 0 and 1 Changelog: * 0.0.1 
     *  initial commit of property</p>
     */
    public static final Property confidence = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#confidence" );
    
    /** <p>A dependency relation pointing from gov to dep. Changelog: * 0.1.0 initial 
     *  commit of property "dependency" * 0.1.1 made the property subproperty of dependencyTrans</p>
     */
    public static final Property dependency = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#dependency" );
    
    /** <p>String denoting the kind of dependency relation</p> */
    public static final Property dependencyRelationType = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#dependencyRelationType" );
    
    /** <p>Changelog: * 0.1.0 initial commit of property * 0.1.1 merged inf model</p> */
    public static final Property dependencyTrans = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#dependencyTrans" );
    
    /** <p>The end index of a character range as defined in http://tools.ietf.org/html/rfc5147#section-2.2.1 
     *  and http://tools.ietf.org/html/rfc5147#section-2.2.2, measured as the gap 
     *  between two characters, starting to count from 0 (the position before the 
     *  first character of a text). Example: Index "2" is the postion between "Mr" 
     *  and "." in "Mr. Sandman". Note: RFC 5147 is re-used for the definition of 
     *  character ranges. RFC 5147 is assuming a text/plain MIME type. NIF builds 
     *  upon Unicode and is content agnostic. Requirement (1): This property has the 
     *  same value the "Character position" of RFC 5147 and it must therefore be an 
     *  xsd:nonNegativeInteger . Requirement (2): The index of the subject string 
     *  MUST be calculated relative to the nif:referenceContext of the subject. If 
     *  available, this is the rdf:Literal of the nif:isString property.</p>
     */
    public static final Property endIndex = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#endIndex" );
    
    /** <p>This property links sentences to their first word. Changelog: * 0.1.1 merged 
     *  inf+val</p>
     */
    public static final Property firstWord = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#firstWord" );
    
    /** <p>Links a nif:ContextCollection to its contexts. Changelog * 0.2.0. renamed 
     *  property since a lot of people where too confused by nif:context</p>
     */
    public static final Property hasContext = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#hasContext" );
    
    /** <p>The first few chars of the nif:anchorOf. Typically used if the nif:anchorOf 
     *  is to long for inclusion as RDF literal.</p>
     */
    public static final Property head = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#head" );
    
    /** <p>This object property models a relation between two nif:Strings. The name "inter" 
     *  is kept generic and can be used to express any kind of relation in between 
     *  (inter) two nif:Strings. Extensions can create rdfs:subPropertyOf for "head", 
     *  "dependent", nif:substring and nif:nextWord. Changelog: * 0.1.0 initial commit 
     *  of property "dependency" * 0.2.0 changed name to "inter" which is more general 
     *  than "dependency".</p>
     */
    public static final Property inter = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#inter" );
    
    /** <p>The reference text as rdf:Literal for this nif:Context resource. NIF requires 
     *  that the reference text (i.e. the context) is always included in the RDF as 
     *  an rdf:Literal. Note, that the isString property is *the* place to keep the 
     *  string itself in RDF. All other nif:Strings and nif:URISchemes relate to the 
     *  text of this property to calculate character position and indices. Changelog 
     *  * 1.0.0: Introduced stable version. * 1.0.1 improved documentation * 1.0.2: 
     *  merged val+inf</p>
     */
    public static final Property isString = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#isString" );
    
    /** <p>A general keyword associated with a string Changelog: * 0.0.1 initial commit 
     *  of property</p>
     */
    public static final Property keyword = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#keyword" );
    
    /** <p>Defines the language of a substring of the context. If the language for the 
     *  nif:Context should be specified, nif:predominantLanguage must be used. see 
     *  nif:predominantLanguage for more info.</p>
     */
    public static final Property lang = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#lang" );
    
    /** <p>This property links sentences to their last word. Changelog: * 0.1.1 fixed 
     *  label * 0.1.2 merged inf+val</p>
     */
    public static final Property lastWord = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#lastWord" );
    
    /** <p>The lemma(s) of the nif:String.</p> */
    public static final Property lemma = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#lemma" );
    
    /** <p>see Towards Web-Scale Collaborative Knowledge Extraction http://svn.aksw.org/papers/2012/PeoplesWeb/public_preprint.pdf? 
     *  page 21 . Changelog: * 0.1.1 Fixed page number</p>
     */
    public static final Property literalAnnotation = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#literalAnnotation" );
    
    /** <p>The inverse of nif:narrowerContext</p> */
    public static final Property narrowerContext = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#narrowerContext" );
    
    /** <p>This property (and nif:previousSentence, nif:nextWord, nif:previousWord and 
     *  their transitive extension) can be used to make resources of nif:Sentence 
     *  and nif:Word traversable, it can not be assumed that no gaps or whitespaces 
     *  between sentences or words exist, i.e. string adjacency is not mandatory. 
     *  The transitivity axioms are included in nif-core-inf.ttl and need to be included 
     *  separately to keep a low reasoning profile. They are modeled after skos:broader 
     *  and skos:broaderTransitive</p>
     */
    public static final Property nextSentence = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#nextSentence" );
    
    /** <p>see nif:nextSentence</p> */
    public static final Property nextSentenceTrans = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#nextSentenceTrans" );
    
    /** <p>see nif:nextSentence</p> */
    public static final Property nextWord = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#nextWord" );
    
    /** <p>see nif:nextSentence</p> */
    public static final Property nextWordTrans = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#nextWordTrans" );
    
    /** <p>The confidence is relative to the tool and can be between 0.0 and 1.0, it 
     *  is for nif:oliaLink and therefore also for nif:oliaCategory. Changelog: 0.2.0 
     *  merged confidence for category and link</p>
     */
    public static final Property oliaConf = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#oliaConf" );
    
    /** <p>This property links a string to a URI from one of the OLiA Annotation model, 
     *  e.g. http://purl.org/olia/penn.owl#NNP Changelog 0.1.1 - added subproperty 
     *  of nif:annotation as per http://svn.aksw.org/papers/2012/PeoplesWeb/public_preprint.pdf? 
     *  page 21</p>
     */
    public static final Property oliaLink = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#oliaLink" );
    
    /** <p>Links to the URI describing the provenance</p> */
    public static final Property oliaProv = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#oliaProv" );
    
    /** <p>This property is used to link to a marl:Opinion. We have not investigated 
     *  marl, so it might be replaced. http://marl.gi2mo.org/?page_id=1#overview . 
     *  InverseOf marl:extractedFrom</p>
     */
    public static final Property opinion = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#opinion" );
    
    /** <p>To include the pos tag as it comes out of the NLP tool as RDF Literal. This 
     *  property is discouraged to use alone, please use oliaLink and oliaCategory. 
     *  We included it, because some people might still want it and will even create 
     *  their own property, if the string variant is missing</p>
     */
    public static final Property posTag = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#posTag" );
    
    /** <p>Defines the predominant language of the text. If this annotation is given 
     *  on a nif:Context, all NIF tools have to treat the text to be in this language 
     *  unless specified differently for a subpart. To change the language for a smaller 
     *  substring nif:lang must be used. This property requires a uri as an argument. 
     *  We expect this to be a URI from the lexvo.org namespace, e.g. http://lexvo.org/id/iso639-3/eng 
     *  using ISO639-3 Examples "The dealer says: "Rien ne va plus!" " has nif:predomintLanguage 
     *  http://lexvo.org/id/iso639-3/eng and nif:lang http://www.lexvo.org/id/iso639-3/fra 
     *  see also: http://www.w3.org/TR/its20/#selection-local Tests for RDFUnit (not 
     *  written yet): - write a test for RDFUnit, so people do not use http://www.lexvo.org/page/iso639-3/eng</p>
     */
    public static final Property predLang = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#predLang" );
    
    /** <p>see nif:nextSentence</p> */
    public static final Property previousSentence = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#previousSentence" );
    
    /** <p>see nif:nextSentence</p> */
    public static final Property previousSentenceTrans = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#previousSentenceTrans" );
    
    /** <p>see nif:nextSentence</p> */
    public static final Property previousWord = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#previousWord" );
    
    /** <p>see nif:nextSentence</p> */
    public static final Property previousWordTrans = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#previousWordTrans" );
    
    /** <p>Links a URI of a string to its reference context of type nif:Context. The 
     *  reference context determines the calculation of begin and end index Each String 
     *  that is not an instance of nif:Context MUST have exactly one reference context. 
     *  Inferences (nif-core-inf.ttl): Instances of nif:Context do have itself as 
     *  reference context, this is inferred automatically, MAY be materialized, as 
     *  well. OWL validation (nif-core-val.ttl): This property is functional. Changelog: 
     *  * 1.0.0: Introduced stable version. * 1.0.1: merged val model</p>
     */
    public static final Property referenceContext = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#referenceContext" );
    
    /** <p>This property links words and other structures to their sentence. Changelog: 
     *  * 0.2.0 Changed domain from nif:Word to nif:Structure, not longer inverseOf 
     *  nif:word</p>
     */
    public static final Property sentence = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#sentence" );
    
    /** <p>Between -1 negative and 1 positive</p> */
    public static final Property sentimentValue = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#sentimentValue" );
    
    /** <p>The URL the context was extracted from, e.g. the blog or news article url. 
     *  Doesn't matter whether it is HTML or XML or plain text. rdfs:range is foaf:Document. 
     *  Subproperty of prov:hadPrimarySource. In case the string comes from another 
     *  NIF String and gives the exact provenance, please use nif:wasConvertedFrom 
     *  or a subProperty thereof. Changelog: * 0.2.0 added subPropertyOf prov:hadPrimarySource 
     *  * 0.2.1 clarification</p>
     */
    public static final Property sourceUrl = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#sourceUrl" );
    
    /** <p>The stem(s) of the nif:String.</p> */
    public static final Property stem = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#stem" );
    
    /** <p>This property together with nif:subString, nif:superString, and their transitive 
     *  extension can be used to express that one string is contained in another one. 
     *  Examples: "a" nif:subString "apple" , "apple" nif:subString "apple". The transitivity 
     *  axioms are included in nif-core-inf.ttl and need to be included separately 
     *  to keep a low reasoning profile. They are modeled after skos:broader and skos:broaderTransitive</p>
     */
    public static final Property subString = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#subString" );
    
    /** <p>transitive version of subString Inferences (nif-core-inf.ttl): Transitive 
     *  definition kept in a different ontology Changelog: * 0.1.0: Initial version 
     *  * 0.1.1: merged inf</p>
     */
    public static final Property subStringTrans = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#subStringTrans" );
    
    /** <p>see nif:subString</p> */
    public static final Property superString = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#superString" );
    
    /** <p>see nif:subStringTrans</p> */
    public static final Property superStringTrans = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#superStringTrans" );
    
    /** <p>The topic of a string Changelog: * 0.0.1 initial commit of property</p> */
    public static final Property topic = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#topic" );
    
    /** <p>This property should be used, when mapping one nif:String or nif:Context to 
     *  another and is often confused with nif:sourceUrl. While nif:sourceUrl is built 
     *  on PROV-O and is used to link the nif:Context to the document URL for provenance 
     *  information, nif:convertedFrom is more precise and pinpoints exact locations 
     *  where a certain NIF String "wasConvertedFrom". nif:wasConvertedFrom is therefore 
     *  used to provide *exact* provenance during a conversion process, e.g. when 
     *  removing tags from XHTML and then linking XPath URIs to NIF index based URIs 
     *  (e.g. RFC 5147 with char=x,y). An example of the usage of this property can 
     *  be found here: http://www.w3.org/TR/its20/#conversion-to-nif Example # "Dublin" 
     *  &lt;http://example.com/myitsservice?informat=html&amp;intype=url&amp;input=http://example.com/doc.html&amp;char=11,17&gt; 
     *  nif:wasConvertedFrom &lt;http://example.com/myitsservice?informat=html&amp;intype=url&amp;input=http://example.com/doc.html&amp;xpath=/html/body[1]/h2[1]/span[1]/text()[1]&gt;.</p>
     */
    public static final Property wasConvertedFrom = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#wasConvertedFrom" );
    
    /** <p>This property links sentences to their words.</p> */
    public static final Property word = m_model.createProperty( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#word" );
    
    /** <p>Individuals of this class are annotations of strings. This class can be used 
     *  if an annotation statement has to be annotated with further information, like 
     *  confidence or annotation provenance (like which tool produced the annotation). 
     *  Changelog: * 0.0.1 initial commit of class</p>
     */
    public static final Resource Annotation = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#Annotation" );
    
    /** <p>A URI Scheme for NIF which is able to refer to a single, consecutive string 
     *  in a context. Note that any scheme subclassing this class, requires the existence 
     *  of beginIndex, endIndex and referenceContext . This is an abstract class and 
     *  should not be serialized.</p>
     */
    public static final Resource CString = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#CString" );
    
    /** <p>An abitrary URI (e.g. a URN) for an arbitrary string of the context. This 
     *  is roughly the same as TextAnnotations are currently implemented in Stanbol.</p>
     */
    public static final Resource CStringInst = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#CStringInst" );
    
    public static final Resource CollectionOccurrence = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#CollectionOccurrence" );
    
    /** <p>The string that serves as a context for its substrings. The Unicode String 
     *  given in the nif:isString property must be used to calculate the begin and 
     *  endIndex for all nif:Strings that have a nif:referenceContext property to 
     *  this URI. For further information, see http://svn.aksw.org/papers/2013/ISWC_NIF/public.pdf 
     *  Changelog * 1.0.1. improved documentation * 1.0.2. merged inf model</p>
     */
    public static final Resource Context = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#Context" );
    
    /** <p>A collection of contexts used to create an unordered set of context via the 
     *  nif:hasContext property. This can be compared to a document collection, but 
     *  here it is a collection of nif:Context and therefore a collection of annotated 
     *  strings, not documents. Open Issues Investigate ordered collections Changelog 
     *  * 0.1.1 improved documentation</p>
     */
    public static final Resource ContextCollection = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#ContextCollection" );
    
    /** <p>cf. https://www.google.de/search?q=Linked-Data+Aware+URI+Schemes+for+Referencing+Text</p> */
    public static final Resource ContextHashBasedString = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#ContextHashBasedString" );
    
    public static final Resource ContextOccurrence = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#ContextOccurrence" );
    
    public static final Resource NormalizedCollectionOccurrence = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#NormalizedCollectionOccurrence" );
    
    public static final Resource NormalizedContextOccurrence = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#NormalizedContextOccurrence" );
    
    /** <p>cf. Linked-Data Aware URI Schemes for Referencing Text Fragments by Sebastian 
     *  Hellmann, Jens Lehmann und S�ren Auer in EKAW 2012 http://jens-lehmann.org/files/2012/ekaw_nif.pdf 
     *  requires the existence of begin, endIndex and referenceContext Changelog: 
     *  * 1.0.0 originally a plain text string was attached to this uri scheme, but 
     *  this was removed later, because of complaints and complexity. * 1.1.0 subclassing 
     *  of nif:CString</p>
     */
    public static final Resource OffsetBasedString = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#OffsetBasedString" );
    
    /** <p>A paragraph. Changelog: * 0.1.1 fixed spelling, added language tag</p> */
    public static final Resource Paragraph = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#Paragraph" );
    
    /** <p>A nif:Phrase can be a nif:String, that is a chunk of several words or a word 
     *  itself (e.g. a NounPhrase as a Named Entity). The term is underspecified and 
     *  can be compatible with many defintitions of phrase. Please subClass it to 
     *  specify the meaning (e.g. for Chunking or Phrase Structure Grammar). Example: 
     *  ((My dog)(also)(likes)(eating (sausage))) Changelog: * 0.1.1 fixed spelling</p>
     */
    public static final Resource Phrase = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#Phrase" );
    
    /** <p>cf. http://tools.ietf.org/html/rfc5147 URIs of this class have to conform 
     *  with the syntax of RFC 5147 in a way that the end on a valid identifier, if 
     *  you remove the prefix. Note that unlike RFC 5147 NIF does not requrire '#' 
     *  URIs. So valid URIs are http://example.org#char=0,28 , http://example.org/whatever/char=0,28 
     *  , http://example.org/nif?char=0,28 Changelog: * 1.1.0 changed subclass to 
     *  CString . * 1.1.1 typo in rdfs:label and extension of comment</p>
     */
    public static final Resource RFC5147String = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#RFC5147String" );
    
    /** <p>A sentence. Changelog: * 0.1.1 fixed spelling, added language tag</p> */
    public static final Resource Sentence = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#Sentence" );
    
    /** <p>Individuals of this class are a string, i.e. Unicode characters, who have 
     *  been given a URI and are used in the subject of an RDF statement. This class 
     *  is abstract and should not be serialized. NIF-Stanbol (nif-stanbol.ttl): subclassOf 
     *  nifs:Annotation because it "annotates" strings for example with begin and 
     *  end index. The class is similar to fise:TextAnnotation Changelog * 1.0.1. 
     *  improved documentation * 1.0.2. merged inf and val model</p>
     */
    public static final Resource String = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#String" );
    
    /** <p>A structure is a more or less arbitrary label for a partitioning of a string. 
     *  We do not follow a strict approach for what a word, phrase, sentence, title, 
     *  paragraph is. These labels enable the definition processes for tool chains, 
     *  e.g. tool analyses nif:Paragraph and calculates term frequency. This is an 
     *  abstract class and should not be serialized. Changelog 0.1.1 not to be serialized 
     *  comment</p>
     */
    public static final Resource Structure = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#Structure" );
    
    /** <p>A title within a text. Changelog: * 0.1.1 fixed spelling, added language tag</p> */
    public static final Resource Title = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#Title" );
    
    /** <p>A URI Scheme for NIF, subclasses need to define guidelines on the URI Scheme 
     *  as well as the text it refers to. This class is just to keep some order, and 
     *  should not be serialized. This is an abstract class and should not be serialized. 
     *  Changelog: * 0.1.1 changed comment</p>
     */
    public static final Resource URIScheme = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#URIScheme" );
    
    /** <p>The Word class represents strings that are tokens or words. A string is a 
     *  Word, if it is a word. We don't nitpic about whether it is a a pronoun, a 
     *  name, a punctuation mark or an apostrophe or whether it is separated by white 
     *  space from another Word or something else. The string 'He enters the room.' 
     *  for example has 5 words. Words are assigned by a tokenizer NIF Implementation. 
     *  Single word phrases might be tagged as nif:Word and nif:Phrase. Example 1: 
     *  "The White House" are three Words separated by whitespace Comment 1: We adopted 
     *  the definition style from foaf:Person, see here: http://xmlns.com/foaf/spec/#term_Person 
     *  We are well aware that the world out there is much more complicated, but we 
     *  are ignorant about it, for the following reasons: Comment 2: 1. NIF has a 
     *  client-server and the client has the ability to dictate the tokenization to 
     *  the server (i.e. the NIF Implementation) by sending properly tokenized NIF 
     *  annotated with nif:Word. All NIF Implementations are supposed to honor and 
     *  respect the current assignment of the Word class. Thus the client should decide 
     *  which NIF Implementation should create the tokenization. Therefore this class 
     *  is not descriptive, but prescriptive. 2. The client may choose to send an 
     *  existing tokenization to a NIF Implementation, with the capability to change 
     *  (for better or for worse) the tokenization. The class has not been named 'Token' 
     *  as the NLP definition of 'token' is descriptive (and not well-defined), while 
     *  the assignment of what is a Word and what not is prescriptive, e.g. "can't" 
     *  could be described as one, two or three tokens or defined as being one, two 
     *  or three words. For further reading, we refer the reader to: By all these 
     *  lovely tokens... Merging conflicting tokenizations by Christian Chiarcos, 
     *  Julia Ritz, and Manfred Stede. Language Resources and Evaluation 46(1):53-74 
     *  (2012) or the short form: http://www.aclweb.org/anthology/W09-3005 There the 
     *  task at hand is to merge two tokenization T_1 and T_2 which is normally not 
     *  the case in the NIF world as tokenization is prescribed, i.e. given as a baseline 
     *  (Note that this ideal state might not be achieved by all implementations.) 
     *  Changelog: * 0.1.1 fixed spelling * 0.2.1 added a proper definition. * 0.2.3 
     *  added examples and clarifications</p>
     */
    public static final Resource Word = m_model.createResource( "http://persistence.uni-leipzig.org/nlp2rdf/ontologies/nif-core#Word" );
    
}

